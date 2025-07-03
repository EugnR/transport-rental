package ru.transport.rent.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.dto.user.RequestUpdateUserDTO;
import ru.transport.rent.dto.user.RequestUserDetailsDTO;
import ru.transport.rent.entity.User;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    /**
     * Метод для сопоставления полей из RequestRegistrationUserDTO в User
     */
    User mapFromRegistrationDto(RequestRegistrationUserDTO userDTO);

    /**
     * Метод для сопоставления полей из User в RequestUserDetailsDTO
     */
    @Mapping(source = "role.name", target = "roleName")
    RequestUserDetailsDTO mapToUserDetailsDto(User user);

    /**
     * Метод для сопоставления полей из RequestUpdateUserDTO в User
     */
    User mapFromUpdateUserDTO(RequestUpdateUserDTO userDTO);
}
