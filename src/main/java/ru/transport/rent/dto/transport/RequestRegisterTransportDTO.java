package ru.transport.rent.dto.transport;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Дто приходящий для регистрации нового транспорта.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestRegisterTransportDTO {

    Boolean canBeRented;
    String transportType;
    String model;
    String color;
    String identifier;
    String description;
    Double latitude;
    Double longitude;
    Double minutePrice;
    Double dayPrice;

}
