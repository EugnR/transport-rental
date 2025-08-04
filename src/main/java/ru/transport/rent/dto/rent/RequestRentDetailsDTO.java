package ru.transport.rent.dto.rent;


import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО возвращаемый на запрос о получении данных об аренде.
 */
@Data
@Builder
public class RequestRentDetailsDTO {

    private Long transportId;
    private Long userId;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Double priceOfUnit;
    private String priceType;
    private Double finalPrice;
}
