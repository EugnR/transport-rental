package ru.transport.rent.repository.impl;

import ru.transport.rent.AbstractMainTest;
import ru.transport.rent.EntityUtils;
import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.transport.rent.entity.User;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransportRepositoryImplTest extends AbstractMainTest {

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private EntityUtils entityUtils;

    @Test
    @DisplayName("Test create transport")
    void testShouldSaveTransport() {
        final User user = entityUtils.createUser();
        Transport newTransport = Transport.builder()
                .owner(user)
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
        Optional<Transport> transport = transportRepository.findById(newTransport.getId());
        assertTrue(transport.isPresent());
        transportRepository.deleteById(newTransport.getId());
    }

    @Test
    @DisplayName("Test delete unrented transport")
    void testShouldDeleteUnrentedTransportById() {
        final User user = entityUtils.createUser();
        Transport newTransport = Transport.builder()
                .owner(user)
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
        transportRepository.deleteById(newTransport.getId());
        Optional<Transport> transport = transportRepository.findById(newTransport.getId());
        assertTrue(transport.isEmpty());
    }
}