package ru.transport.rent.service.rent;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.transport.rent.config.TransportTypesConfig;
import ru.transport.rent.config.UtilVarsConfig;
import ru.transport.rent.dto.rent.TransportAroundInfoDTO;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.exceptions.InvalidRentTypeException;
import ru.transport.rent.exceptions.InvalidTransportTypeException;
import ru.transport.rent.exceptions.OwnerMismatchException;
import ru.transport.rent.mapper.transport.TransportMapper;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.security.AuthenticationService;
import ru.transport.rent.utils.CustomUtils;
/**
 * Реализация интерфейса RentService для обслуживания RentController.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RentServiceImpl implements RentService {

    private final TransportMapper transportMapper;
    private final TransportTypesConfig transportTypesConfig;
    private final TransportRepository transportRepository;
    private final RentRepository rentRepository;

    /**
     * Метод валидирует полученный тип транспорта и выбирает какой поиск производить, а также переводит метры в километры.
     * @param latitude  широта центра круга поиска.
     * @param longitude долгота центра круга поиска.
     * @param radius    радиус поиска в метрах.
     * @param type      тип транспортного средства.
     * @return список подходящих транспортных средств.
     */
    @Override
    public List<TransportAroundInfoDTO> findAvailableTransport(
            final Double latitude,
            final Double longitude,
            final Double radius,
            final String type
    ) {
        if (!transportTypesConfig.getValidTypesAsSet().contains(
                CustomUtils.CapitalizeFirst(type))) {
            throw new InvalidTransportTypeException("Invalid transport type: " + type);
        }
        if (UtilVarsConfig.ALL_TRANSPORT.equals(type)) {
            return getAllTransportInRadius(latitude, longitude, radius / UtilVarsConfig.METERS_TO_KILOMETERS);
        } else {
            return getSpecificTransportInRadius(latitude, longitude, radius / UtilVarsConfig.METERS_TO_KILOMETERS, type);
        }
    }

    /**
     * Метод для поиска любого доступного транспорта в радиусе.
     */
    @Override
    public List<TransportAroundInfoDTO> getAllTransportInRadius(
            final Double latitude,
            final Double longitude,
            final Double radius
    ) {
        return transportRepository.findAllAvailableTransportInRadius(latitude, longitude, radius)
                .stream()
                .map(transportMapper::mapTransportToTransportInfoDto)
                .toList();
    }

    /**
     * Метод для поиска доступного транспорта определённого типа в радиусе.
     */
    @Override
    public List<TransportAroundInfoDTO> getSpecificTransportInRadius(
            final Double latitude,
            final Double longitude,
            final Double radius,
            final String type
    ) {
        return transportRepository.findSpecificTransportsInRadius(latitude, longitude, radius, type)
                .stream()
                .map(transportMapper::mapTransportToTransportInfoDto)
                .toList();
    }

    /**
     * Метод для создания новой аренды.
     *
     * @param transportId id транспорта, который берётся в аренду.
     * @param typeOfRent  - тип аренды (минуты или дни)
     */
    @Override
    public void createRent(Long transportId, String typeOfRent) {

        typeOfRent = CustomUtils.CapitalizeFirst(typeOfRent);
        Double price;

        User user = AuthenticationService.getUserFromSecurityContext();

        Transport transportToSave = transportRepository.findById(transportId)
                .orElseThrow(() -> new EntityNotFoundException("Transport for rent is not found"));

        if (typeOfRent.equals("Minutes")) { price = transportToSave.getMinutePrice(); }
        else if (typeOfRent.equals("Days")) { price = transportToSave.getDayPrice(); }
        else { throw new InvalidRentTypeException("Invalid rent type: " + typeOfRent); }

        if (transportToSave.getOwner().equals(user)) {
            throw new OwnerMismatchException("Owner can't rent his own transport");
        }

        Rent rent = Rent.builder()
                .transport(transportToSave)
                .user(user)
                .timeStart(LocalDateTime.now())
                .timeEnd(null)      //установится при завершении аренды
                .priceOfUnit(price)
                .priceType(typeOfRent)
                .finalPrice(null)   //установится при завершении аренды
                .build();
        rentRepository.save(rent);
    }
}
