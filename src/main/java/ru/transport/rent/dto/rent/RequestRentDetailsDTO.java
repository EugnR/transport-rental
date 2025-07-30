package ru.transport.rent.dto.rent;


import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import ru.transport.rent.entity.Transport;
import ru.transport.rent.entity.User;

/**
 * ДТО возвращаемый на запрос о получении данных об аренде.
 */
@Data
@Builder
public class RequestRentDetailsDTO {

    private Transport transportId;
    private User userId;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Double priceOfUnit;
    private String priceType;
    private Double finalPrice;
}
