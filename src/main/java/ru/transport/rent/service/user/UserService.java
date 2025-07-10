package ru.transport.rent.service.user;

import java.security.Principal;

import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.dto.user.RequestSignInUserDTO;
import ru.transport.rent.dto.user.RequestUpdateUserDTO;
import ru.transport.rent.dto.user.RequestUserDetailsDTO;


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
    String signInUser(RequestSignInUserDTO requestSignInUserDTO);

    /**
     * Метод для сбора информации пользователю о себе.
     * @param principal сам пользователь
     * @return дто с именем, ролью и балансом
     */
    RequestUserDetailsDTO getUserDetails(Principal principal);

    /**
     * Метод для обновления информации об аккаунте.
     */
    void updateUserDetails(RequestUpdateUserDTO requestUpdateUserDTO);
}
