package ru.transport.rent.repository.impl;

import ru.transport.rent.AbstractMainTest;
import ru.transport.rent.EntityUtils;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserRepositoryTest extends AbstractMainTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test create user")
    void testShouldCreateUser() {

        User newUser = User.builder()
                .userName("test")
                .password("test")
                .isAdmin(false)
                .balance(100.0)
                .build();
        userRepository.save(newUser);
        Optional<User> user = userRepository.findById(newUser.getId());
        assertTrue(user.isPresent());
        userRepository.deleteById(newUser.getId());
    }


    @Test
    @DisplayName("Test delete user without transport and rent")
    void testShouldDeleteUser() {

        User newUser = User.builder()
                .userName("test")
                .password("test")
                .isAdmin(false)
                .balance(100.0)
                .build();
        userRepository.save(newUser);
        userRepository.deleteById(newUser.getId());
        Optional<User> user = userRepository.findById(newUser.getId());
        assertTrue(user.isEmpty());
    }

}