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
        User createUser = User.builder()
                .entityId(1L)
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
                .entityId(id)
                .userName("test")
                .build();
        userRepository.save(createUser);
        userRepository.delete(id);
        Optional<User> user = userRepository.getById(id);
        assertTrue(user.isEmpty());
    }

}