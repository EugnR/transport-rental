package ru.transport.rent.controller;

import java.security.Principal;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.transport.rent.dto.user.RequestRegistrationUserDTO;
import ru.transport.rent.dto.user.RequestSignInUserDTO;
import ru.transport.rent.dto.user.RequestUpdateUserDTO;
import ru.transport.rent.service.user.UserService;


/**
 * Контроллер для регистрации и авторизации.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/Account")
public class UserController {

    private final UserService userService;


    /**
     * Метод для регистрации пользователя.
     */
    @PostMapping("/SignUp")
    public ResponseEntity<?> registrationUser(@RequestBody final RequestRegistrationUserDTO registrationUserDTO) {
        userService.registerUser(registrationUserDTO);
        return ResponseEntity.ok()
                .build();
    }


    /**
     * Метод для получения JWT токена.
     */
    @PostMapping("/SignIn")
    public ResponseEntity<?> signInUser(@RequestBody final RequestSignInUserDTO requestSignInUserDTO) {
        return ResponseEntity.ok()
                .body(userService.signInUser(requestSignInUserDTO));
    }

    /**
     * Метод для возвращения пользователю информации об его аккаунте.
     */
    @GetMapping("/Me")
    public ResponseEntity<?> me(final Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized lol");
        }
        return ResponseEntity.ok(userService.getUserDetails(principal));
    }

    /**
     * Метод для обновления аккаунта.
     */
    @PutMapping("/Update")
    public ResponseEntity<?> updateAccount(@RequestBody final RequestUpdateUserDTO updateUserDTO, final Principal principal) {
        return ResponseEntity.ok()
                .body(userService.updateUserDetails(updateUserDTO, principal));
    }

}
