package ru.transport.rent.dto.transport;

import lombok.Builder;
import lombok.Data;

/**
 * Дто приходящий для регистрации нового транспорта.
 */
@Data
@Builder
public class RequestRegisterTransportDTO {

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
