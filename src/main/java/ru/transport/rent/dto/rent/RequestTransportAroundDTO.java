package ru.transport.rent.dto.rent;

import lombok.Builder;
import lombok.Data;

/**
 * Дто приходящий с запросом на поиск транспорта в округе.
 */
@Data
@Builder
public class RequestTransportAroundDTO {

    private Double latitude;
    private Double longitude;
    private Double radius;
    private String type;

}
