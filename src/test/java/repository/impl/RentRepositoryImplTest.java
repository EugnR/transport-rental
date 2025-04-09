package repository.impl;

import entity.Rent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.RentRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RentRepositoryImplTest {

    @Test
    @DisplayName("Test create rent")
    void testShouldSaveRent() {
        RentRepository rentRepository = RentRepositoryImpl.getInstance();
        Rent newRent = Rent.builder()
                .transportId(1L)
                .id(1L)
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);
        Optional<Rent> rent = rentRepository.getById(1L);
        assertTrue(rent.isPresent());
        rentRepository.deleteById(1L);

    }

    @Test
    @DisplayName("Test delete rent")
    void testShouldDeleteRentById() {
        RentRepository rentRepository = RentRepositoryImpl.getInstance();
        Rent newRent = Rent.builder()
                .transportId(1L)
                .id(1L)
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);
        rentRepository.deleteById(1L);
        Optional<Rent> rent = rentRepository.getById(1L);
        assertTrue(rent.isEmpty());
    }
}