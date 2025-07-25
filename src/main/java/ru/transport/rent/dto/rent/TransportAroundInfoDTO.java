package ru.transport.rent.dto.rent;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО помещаемый в список, который возвращается на запрос
 * о поиске доступных транспортных средств поблизости.
 */
@Data
@Builder
public class TransportAroundInfoDTO {

    private Boolean canBeRented;
    private String transportType;
    private String model;
    private String color;
    private String identifier;
    private String description;
    private Double latitude;
    private Double longitude;
    private Double minutePrice;
    private Double dayPrice;
}