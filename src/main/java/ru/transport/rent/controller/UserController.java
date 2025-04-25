package ru.transport.rent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.dto.user.RequestSingInUserDTO;
import ru.transport.rent.service.user.UserService;

import lombok.RequiredArgsConstructor;


/**
 * Контроллер для регистрации и авторизации.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    /**
     * Метод для регистрации пользователя.
     */
    @PostMapping("/registration")
    public ResponseEntity<?> registrationUser(@RequestBody final RequestRegistrationUserDTO registrationUserDTO) {
        userService.registerUser(registrationUserDTO);
        return ResponseEntity.ok()
                .build();
    }


    /**
     * Метод для получения JWT токена.
     */
    @PostMapping("/sing-in")
    public ResponseEntity<?> singInUser(@RequestBody final RequestSingInUserDTO requestSingInUserDTO) {
        return ResponseEntity.ok()
                .body(userService.singInUser(requestSingInUserDTO));
    }

}
