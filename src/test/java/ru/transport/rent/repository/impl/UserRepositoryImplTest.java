package ru.transport.rent.repository.impl;

import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("Test delete user without transport and rent")
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

    @Test
    @DisplayName("Test not deleting user with rent")
    void testShouldNotDeleteUserWithRent() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        RentRepository rentRepository = RentRepositoryImpl.getInstance();

        User newUser = User.builder()
                .userName("test")
                .build();
        userRepository.save(newUser);

        Rent newRent = Rent.builder()
                .transportId(999L)
                .userId(newUser.getId())
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);

        assertThrows(IllegalStateException.class, () -> userRepository.deleteById(newUser.getId()),
                "Ожидалось, что при попытке удаления пользователя выйдет исключение, тк он прикреплён к аренде");
        Optional<User> user = userRepository.getById(newUser.getId());
        assertTrue(user.isPresent(), "Пользователь удалился, хотя не должен был");
        rentRepository.deleteById(newRent.getId());
        userRepository.deleteById(newUser.getId());
        user = userRepository.getById(newUser.getId());
        assertTrue(user.isEmpty(), "Пользователь не удалился, хотя аренда, к которой он был привязан, была удалена");
    }

    @Test
    @DisplayName("Test not deleting user with transport")
    void testShouldNotDeleteUserWithTransport() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();

        User newUser = User.builder()
                .userName("test")
                .build();
        userRepository.save(newUser);

        Transport newTransport = Transport.builder()
                .ownerId(newUser.getId())
                .canBeRented(true)
                .transportType("Car")
                .model("Skoda")
                .color("White")
                .identifier("м777мм")
                .description("тестовое описание")
                .latitude(55.751244)
                .longitude(37.618423)
                .minutePrice(10.0)
                .dayPrice(3500.0)
                .build();
        transportRepository.save(newTransport);

        assertThrows(IllegalStateException.class, () -> userRepository.deleteById(newUser.getId()),
                "Ожидалось, что при попытке удаления пользователя выйдет исключение, тк он прикреплён к транспорту");
        Optional<User> user = userRepository.getById(newUser.getId());
        assertTrue(user.isPresent(), "Пользователь удалился, хотя не должен был");
        transportRepository.deleteById(newTransport.getId());
        userRepository.deleteById(newUser.getId());
        user = userRepository.getById(newUser.getId());
        assertTrue(user.isEmpty(), "Пользователь не удалился, хотя транспорт, к которому он был привязан, был удален");
    }


    @Test
    @DisplayName("Test not deleting user with transport and rent")
    void testShouldNotDeleteUserWithTransportAndRent() {
        UserRepository userRepository = UserRepositoryImpl.getInstance();
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();
        RentRepository rentRepository = RentRepositoryImpl.getInstance();

        User newUser = User.builder()
                .userName("test")
                .build();
        userRepository.save(newUser);

        Transport newTransport = Transport.builder()
                .ownerId(newUser.getId())
                .canBeRented(true)
                .transportType("Car")
                .model("Skoda")
                .color("White")
                .identifier("м777мм")
                .description("тестовое описание")
                .latitude(55.751244)
                .longitude(37.618423)
                .minutePrice(10.0)
                .dayPrice(3500.0)
                .build();
        transportRepository.save(newTransport);

        Rent newRent = Rent.builder()
                .transportId(newTransport.getId())
                .userId(newUser.getId())
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);

        assertThrows(IllegalStateException.class, () -> userRepository.deleteById(newUser.getId()),
                "Ожидалось, что при попытке удаления пользователя выйдет исключение, тк он прикреплён к аренде");
        rentRepository.deleteById(newRent.getId());
        assertThrows(IllegalStateException.class, () -> userRepository.deleteById(newUser.getId()),
                "Ожидалось, что при попытке удаления пользователя выйдет исключение, тк он прикреплён к транспорту");
        transportRepository.deleteById(newTransport.getId());

        userRepository.deleteById(newUser.getId());
        Optional<User> user = userRepository.getById(newUser.getId());
        assertTrue(user.isEmpty(), "Пользователь не удалился, хотя транспорт, к которому он был привязан, был удален");

    }

}