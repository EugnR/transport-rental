package ru.transport.rent.mapper.transport;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.transport.rent.dto.transport.RequestRegisterTransportDTO;
import ru.transport.rent.dto.transport.RequestTransportDetailsDTO;
import ru.transport.rent.entity.Transport;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransportMapper {

    /**
     * Метод для сопоставления полей из RequestRegisterTransportDTO в Transport.
     */
    Transport mapRegisterTransportDtoToTransport(RequestRegisterTransportDTO registerTransportDTO);

    /**
     * Метод для сопоставления полей из Transport в RequestTransportDetails.
     */
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.userName", target = "ownerUsername")
    RequestTransportDetailsDTO mapTransportToRequestTransportDetails(Transport transport);
}
