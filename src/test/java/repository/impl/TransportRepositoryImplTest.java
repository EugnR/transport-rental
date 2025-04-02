package repository.impl;

import entity.Transport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.TransportRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransportRepositoryImplTest {

    @Test
    @DisplayName("Test create transport")
    void TestSave() {
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();
        Transport newTransport = Transport.builder()
                .transportId(1L)
                .ownerId(1L)
                .canBeRented(true)
                .transportType("Car")
                .model("Skoda")
                .color("White")
                .identifier("м777мм")
                .description("тестовое описание")
                .latitude(55.751244)
                .longitude(37.618423)
                .minutePrice(10)
                .dayPrice(3500)
                .build();
        transportRepository.save(newTransport);
        Optional<Transport> transport = transportRepository.getById(1L);
        assertTrue(transport.isPresent());
        transportRepository.deleteById(1L);
    }

    @Test
    @DisplayName("Test delete transport")
    void TestDeleteById() {
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();
        long id = 1L;
        Transport newTransport = Transport.builder()
                .transportId(1L)
                .ownerId(1L)
                .canBeRented(true)
                .transportType("Car")
                .model("Skoda")
                .color("White")
                .identifier("м777мм")
                .description("тестовое описание")
                .latitude(55.751244)
                .longitude(37.618423)
                .minutePrice(10)
                .dayPrice(3500)
                .build();
        transportRepository.save(newTransport);
        transportRepository.deleteById(1L);
        Optional<Transport> transport = transportRepository.getById(1L);
        assertTrue(transport.isEmpty());
    }


}