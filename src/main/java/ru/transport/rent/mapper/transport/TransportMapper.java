package ru.transport.rent.mapper.transport;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.entity.Transport;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransportMapper {

    /**
     * Метод для сопоставления полей из RequestRegisterTransportDTO в Transport
     */
    Transport mapRegisterTransportDtoToTransport(RequestRegisterTransportDTO registerTransportDTO);
}
