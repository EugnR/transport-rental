package ru.transport.rent.service.transport;

import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.dto.transport.RequestTransportDetailsDTO;

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
}
