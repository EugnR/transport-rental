package repository.impl;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import entity.User;
import repository.UserRepository;


class UserRepositoryImplTest {

    @Test
    @DisplayName("Test create user")
    void testShouldCreateUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        User createUser = User.builder()
                .userId(1L)
                .userName("test")
                .build();
        userRepository.save(createUser);
        Optional<User> user = userRepository.getById(1L);
        assertTrue(user.isPresent());
        userRepository.delete(1L);
    }


    @Test
    @DisplayName("Test delete user")
    void testShouldDeleteUser() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        long id = 1L;
        User createUser = User.builder()
                .userId(id)
                .userName("test")
                .build();
        userRepository.save(createUser);
        userRepository.delete(id);
        Optional<User> user = userRepository.getById(id);
        assertTrue(user.isEmpty());
    }

}