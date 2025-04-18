package ru.transport.rent.service.user;

import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.dto.user.RequestSingInUserDTO;


/**
 * Интерфейс для user.
 */
public interface UserService {

    /**
     * Метод для регистрации пользователя.
     */
    void registerUser(RequestRegistrationUserDTO requestRegistrationUserDTO);

    /**
     * Метод для получения JWT токена.
     */
    String singInUser(RequestSingInUserDTO requestSingInUserDTO);

}
