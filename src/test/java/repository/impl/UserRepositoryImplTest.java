package repository.impl;

import entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;


class UserRepositoryImplTest {

    @Test
    @DisplayName("Test create user")
    void testShouldCreateUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        User newUser = User.builder()
                .userName("test")
                .build();
        userRepository.save(newUser);
        Optional<User> user = userRepository.getById(newUser.getId());
        assertTrue(user.isPresent());
        userRepository.deleteById(newUser.getId());
    }


    @Test
    @DisplayName("Test delete user")
    void testShouldDeleteUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        User newUser = User.builder()
                .userName("test")
                .build();
        userRepository.save(newUser);
        userRepository.deleteById(newUser.getId());
        Optional<User> user = userRepository.getById(newUser.getId());
        assertTrue(user.isEmpty());
    }

}