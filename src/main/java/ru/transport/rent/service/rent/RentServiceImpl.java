package ru.transport.rent.service.rent;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.transport.rent.config.TransportTypesConfig;
import ru.transport.rent.dto.transport.TransportAroundInfoDTO;
import ru.transport.rent.exceptions.InvalidTransportTypeException;
import ru.transport.rent.mapper.transport.TransportMapper;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.utils.TransportUtils;

/**
 * Реализация интерфейса RentService для обслуживания RentController.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RentServiceImpl implements RentService {

    private final Double metersToKilometers = 1000.0;

    private final RentRepository rentRepository;
    private final TransportMapper transportMapper;
    private final TransportTypesConfig transportTypesConfig;

    /**
     * Метод валидирует полученный тип транспорта и выбирает какой поиск производить, а также переводит метры в километры.
     * @param latitude  широта центра круга поиска.
     * @param longitude долгота центра круга поиска.
     * @param radius    радиус поиска в метрах.
     * @param type      тип транспортного средства.
     * @return список подходящих транспортных средств.
     */
    public List<TransportAroundInfoDTO> findAvaliableTransport(Double latitude, Double longitude, Double radius, String type) {
        if (!transportTypesConfig.getValidTypesAsSet().contains(
                TransportUtils.normalizeTransportType(type))) {
            throw new InvalidTransportTypeException("Invalid transport type: " + type);
        }
        if (type.equals("All")) {
            return getAllTransportInRadius(latitude, longitude, radius / metersToKilometers);
        } else {
            return getSpecificTransportInRadius(latitude, longitude, radius / metersToKilometers, type);
        }
    }

    /**
     * Метод для поиска любого доступного транспорта в радиусе.
     */
    @Override
    public List<TransportAroundInfoDTO> getAllTransportInRadius(Double latitude, Double longitude, Double radius) {
        return rentRepository.findAllAvailableTransportInRadius(latitude, longitude, radius)
                .stream()
                .map(transportMapper::mapTransportToTransportInfoDto)
                .toList();
    }

    /**
     * Метод для поиска доступного транспорта определённого типа в радиусе.
     */
    @Override
    public List<TransportAroundInfoDTO> getSpecificTransportInRadius(Double latitude, Double longitude, Double radius, String type) {
        return rentRepository.findSpecificTransportsInRadius(latitude, longitude, radius, type)
                .stream()
                .map(transportMapper::mapTransportToTransportInfoDto)
                .toList();
    }
}
