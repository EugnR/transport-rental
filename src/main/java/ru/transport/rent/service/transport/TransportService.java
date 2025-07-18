package ru.transport.rent.service.transport;

import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.dto.transport.RequestTransportDetailsDTO;
import ru.transport.rent.dto.transport.RequestUpdateTransportDTO;

/**
 * Интерфейс для transport.
 */
public interface TransportService {

    /**
     * Метод для регистрации нового транспорта.
     */
    void registerTransport(RequestRegisterTransportDTO registerTransportDTO);

    /**
     * Метод для возвращения информации о транспорте по id.
     */
    RequestTransportDetailsDTO getTransportDetails(Long id);

    /**
     * Метод для смены информации о транспорте по id.
     */
    void updateTransportDetails(Long id, RequestUpdateTransportDTO updateTransportDTO);

    /**
     * Метод для удаления транспорта по id.
     */
    void deleteTransport(Long id);

}
