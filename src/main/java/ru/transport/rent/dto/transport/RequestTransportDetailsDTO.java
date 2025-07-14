package ru.transport.rent.dto.transport;

import lombok.Builder;
import lombok.Data;

/**
 * ДТО возвращаемый на запрос о получении данных о транспорте.
 */
@Data
@Builder
public class RequestTransportDetailsDTO {

    private Long ownerId;
    private String ownerUsername;
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
