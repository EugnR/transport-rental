package ru.transport.rent.service.rent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.transport.rent.config.TransportTypesConfig;
import ru.transport.rent.config.UtilVarsConfig;
import ru.transport.rent.dto.rent.RequestRentDetailsDTO;
import ru.transport.rent.dto.rent.TransportAroundInfoDTO;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.exceptions.InvalidRentTypeException;
import ru.transport.rent.exceptions.InvalidTransportTypeException;
import ru.transport.rent.exceptions.OwnerMismatchException;
import ru.transport.rent.exceptions.SelfRentalNotAllowedException;
import ru.transport.rent.mapper.rent.RentMapper;
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
    private final RentMapper rentMapper;

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
                CustomUtils.capitalizeFirst(type))) {
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
    public void createRent(final Long transportId, final String typeOfRent) {

        final String normalizedTypeOfRent = CustomUtils.capitalizeFirst(typeOfRent);
        final Double price;

        final Transport rentedTransport = transportRepository.findById(transportId)
                .orElseThrow(() -> new EntityNotFoundException("Transport for rent is not found"));

        if (UtilVarsConfig.MINUTES.equals(normalizedTypeOfRent)) {
            price = rentedTransport.getMinutePrice();
        } else if (UtilVarsConfig.DAYS.equals(normalizedTypeOfRent)) {
            price = rentedTransport.getDayPrice();
        } else {
            throw new InvalidRentTypeException("Invalid rent type: " + normalizedTypeOfRent);
        }

        final User user = AuthenticationService.getUserFromSecurityContext();

        if (rentedTransport.getOwner().equals(user)) {
            throw new SelfRentalNotAllowedException("Owner can't rent his own transport");
        }

        rentedTransport.setCanBeRented(false);
        transportRepository.save(rentedTransport);

        final Rent rent = Rent.builder()
                .transport(rentedTransport)
                .user(user)
                .timeStart(LocalDateTime.now())
                .timeEnd(null)      //установится при завершении аренды
                .priceOfUnit(price)
                .priceType(normalizedTypeOfRent)
                .finalPrice(null)   //установится при завершении аренды
                .build();

        rentRepository.save(rent);
    }

    /**
     * Метод для заканчивания аренды.
     */
    @Override
    public void endRent(final Long rentId, final Double latitude, final Double longitude) {
        final Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new EntityNotFoundException("Rent is not found"));

        final User user = AuthenticationService.getUserFromSecurityContext();
        if (!user.equals(rent.getUser())) {
            throw new OwnerMismatchException("Only owner can end his rent");
        }

        final Transport transport = rent.getTransport();
        transport.setLatitude(latitude);
        transport.setLongitude(longitude);

        rent.setTimeEnd(LocalDateTime.now());
        final Duration rentDuration = Duration.between(rent.getTimeStart(), rent.getTimeEnd());
        final String rentType = rent.getPriceType();

        if (UtilVarsConfig.MINUTES.equals(rentType)) {
            rent.setFinalPrice(transport.getMinutePrice() * rentDuration.toMinutes());
        } else if (UtilVarsConfig.DAYS.equals(rentType)) {
            long days = rentDuration.toDays();
            if (days == 0) {
                days = 1;
            }
            rent.setFinalPrice(transport.getDayPrice() * days);
        }

        transport.setCanBeRented(true);
        transportRepository.save(transport);
        rentRepository.save(rent);
    }

    /**
     * Метод для возвращения информации об аренде.
     */
    @Override
    public RequestRentDetailsDTO getRentDetails(final Long rentId) {
        final Rent rent = rentRepository.findById(rentId).orElseThrow(() -> new EntityNotFoundException("Rent is not found"));
        final User user = AuthenticationService.getUserFromSecurityContext();
        final User owner = rent.getTransport().getOwner();
        final User renter = rent.getUser();

        if (!user.equals(owner) && !user.equals(renter)) {
            throw new OwnerMismatchException("Only owner of rented transport or its renter can get rent information");
        }

        return rentMapper.mapRentToRequestRentDetailsDto(rent);
    }

    /**
     * Метод для получения истории аренд пользователя.
     */
    @Override
    public List<RequestRentDetailsDTO> getUserRentHistory() {
        User user = AuthenticationService.getUserFromSecurityContext();

        return rentRepository.findByUserIdOrderByTimeStartDesc(user.getId())
                .stream()
                .map(rentMapper::mapRentToRequestRentDetailsDto)
                .toList();
    }
}
