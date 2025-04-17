package ru.transport.rent;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.transport.rent.entity.Rent;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;
import ru.transport.rent.repository.RentRepository;
import ru.transport.rent.repository.TransportRepository;
import ru.transport.rent.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class EntityUtils {

    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final TransportRepository transportRepository;


    @Transactional
    public User createUser() {
        return userRepository.save(User.builder()
                .userName("admin")
                .password("admin")
                .balance(0.0)
                .isAdmin(Boolean.FALSE)
                .build());
    }


    @Transactional
    public Transport createTransport(final User user) {
        return transportRepository.save(Transport.builder()
                .color("black")
                .owner(user)
                .canBeRented(Boolean.TRUE)
                .transportType("type")
                .model("model")
                .identifier("identifier")
                .latitude(0.0)
                .longitude(0.0)
                .minutePrice(1.0)
                .dayPrice(2.0)
                .build());
    }


    @Transactional
    public Rent createRent(final User user, final Transport transport) {
        return rentRepository.save(Rent.builder()
                .transport(transport)
                .user(user)
                .priceType("PriceType")
                .timeStart(LocalDateTime.now())
                .timeEnd(LocalDateTime.now()
                        .plusHours(1L))
                        .finalPrice(100.0)
                .build());
    }

}
