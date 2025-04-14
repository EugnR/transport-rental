package repository.impl;

import entity.Rent;
import entity.Transport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.RentRepository;
import repository.TransportRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransportRepositoryImplTest {

    @Test
    @DisplayName("Test create transport")
    void testShouldSaveTransport() {
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();
        Transport newTransport = Transport.builder()
                .ownerId(999L)
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
        Optional<Transport> transport = transportRepository.getById(newTransport.getId());
        assertTrue(transport.isPresent());
        transportRepository.deleteById(newTransport.getId());
    }

    @Test
    @DisplayName("Test delete unrented transport")
    void testShouldDeleteUnrentedTransportById() {
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();
        Transport newTransport = Transport.builder()
                .ownerId(999L)
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
        Optional<Transport> transport = transportRepository.getById(newTransport.getId());
        assertTrue(transport.isEmpty());
    }

    @Test
    @DisplayName("Test not deleting rented transport")
    void testShouldNotDeleteRentedTransportById() {
        TransportRepository transportRepository = TransportRepositoryImpl.getInstance();
        RentRepository rentRepository = RentRepositoryImpl.getInstance();
        Long transportOwnerId = 999L;
        Transport newTransport = Transport.builder()
                .ownerId(transportOwnerId)
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
                .userId(transportOwnerId)
                .timeStart(LocalDateTime.parse("2025-04-02T12:00:00"))
                .timeEnd(LocalDateTime.parse("2025-04-02T13:00:00"))
                .priceOfUnit(10.0)
                .priceType("minutePrice")
                .finalPrice(600.0)
                .build();
        rentRepository.save(newRent);


        assertThrows(IllegalStateException.class, () -> transportRepository.deleteById(newTransport.getId()),
                "Ожидалось, что при попытке удаления транспорта выйдет исключение, тк он прикреплён к аренде");
        Optional<Transport> transport = transportRepository.getById(newTransport.getId());
        assertTrue(transport.isPresent(), "Транспорт удалился, хотя не должен был");
        rentRepository.deleteById(newRent.getId());
        transportRepository.deleteById(newTransport.getId());
        transport = transportRepository.getById(newTransport.getId());
        assertTrue(transport.isEmpty(), "Транспорт не удалился, хотя аренда, к которой он был привязан, была удалена");

    }




}