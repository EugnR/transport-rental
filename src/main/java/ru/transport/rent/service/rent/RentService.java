package ru.transport.rent.service.rent;

import java.util.List;

import ru.transport.rent.dto.rent.RequestRentDetailsDTO;
import ru.transport.rent.dto.rent.TransportAroundInfoDTO;

/**
 * Интерфейс для rent.
 */
public interface RentService {

    /**
     * Метод валидирует полученный тип транспорта и выбирает какой поиск производить.
     * @param latitude  широта центра круга поиска.
     * @param longitude долгота центра круга поиска.
     * @param radius    радиус поиска в метрах.
     * @param type      тип транспортного средства.
     * @return список подходящих транспортных средств.
     */
    List<TransportAroundInfoDTO> findAvailableTransport(Double latitude, Double longitude, Double radius, String type);

    /**
     * Метод для поиска любого доступного транспорта в радиусе.
     */
    List<TransportAroundInfoDTO> getAllTransportInRadius(Double latitude, Double longitude, Double radius);

    /**
     * Метод для поиска доступного транспорта определённого типа в радиусе.
     */
    List<TransportAroundInfoDTO> getSpecificTransportInRadius(Double latitude, Double longitude, Double radius, String type);

    /**
     * Метод для создания новой аренды.
     * @param transportId id транспорта, который берётся в аренду.
     * @param typeOfRent - тип аренды (минуты или дни)
     */
    void createRent(Long transportId, String typeOfRent);

    /**
     * Метод для заканчивания аренды.
     */
    void endRent(Long rentId, Double latitude, Double longitude);

    /**
     * Метод для возвращения информации об аренде.
     */
    RequestRentDetailsDTO getRentDetails(Long rentId);
}
