package ru.transport.rent.mapper.rent;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.transport.rent.dto.rent.RequestRentDetailsDTO;
import ru.transport.rent.entity.Rent;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RentMapper {

    /**
     * Метод для сопоставления полей из Rent в RequestRentDetailsDTO.
     */
    @Mapping(source = "transport.id", target = "transportId")
    @Mapping(source = "user.id", target = "userId")
    RequestRentDetailsDTO mapRentToRequestRentDetailsDto(Rent rent);
}
