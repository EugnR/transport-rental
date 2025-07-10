package ru.transport.rent.service.transport;

import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;

/**
 * Интерфейс для transport.
 */
public interface TransportService {

    /**
     * Метод для регистрации нового транспорта.
     */
    void registerTransport(RequestRegisterTransportDTO registerTransportDTO);
}
