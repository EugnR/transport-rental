package ru.transport.rent.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.entity.User;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User mapRegistration(RequestRegistrationUserDTO userDTO);

}
