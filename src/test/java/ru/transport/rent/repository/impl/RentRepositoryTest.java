package ru.transport.rent.repository.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ru.transport.rent.AbstractMainTest;
import ru.transport.rent.EntityUtils;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.repository.RentRepository;

class RentRepositoryTest extends AbstractMainTest {

    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private EntityUtils entityUtils;


    @Test
    @DisplayName("Test create rent")
    void testShouldSaveRent() {
        final User user = entityUtils.createUser();
        final Transport transport = entityUtils.createTransport(user);
        Rent newRent = Rent.builder()
                .transport(transport)
                .user(user)
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);
        Optional<Rent> rent = rentRepository.findById(newRent.getId());
        assertTrue(rent.isPresent());
        rentRepository.deleteById(1L);

    }

    @Test
    @DisplayName("Test delete rent")
    void testShouldDeleteRentById() {
        final User user = entityUtils.createUser();
        final Transport transport = entityUtils.createTransport(user);
        Rent newRent = Rent.builder()
                .transport(transport)
                .user(user)
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);
        rentRepository.deleteById(newRent.getId());
        Optional<Rent> rent = rentRepository.findById(newRent.getId());
        assertTrue(rent.isEmpty());
    }
}