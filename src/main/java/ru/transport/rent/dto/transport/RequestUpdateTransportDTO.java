package ru.transport.rent.dto.transport;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО, приходящий на сервис с запросом на смену данных о транспорте.
 */
@Data
@Builder
public class RequestUpdateTransportDTO {

    private Boolean canBeRented;
    private String model;
    private String color;
    private String identifier;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double minutePrice;
    private Double dayPrice;
}
