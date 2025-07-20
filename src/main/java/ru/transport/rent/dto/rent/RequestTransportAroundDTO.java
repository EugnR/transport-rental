package ru.transport.rent.dto.rent;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Дто приходящий с запросом на поиск транспорта в округе.
 */
@Data
@Builder
public class RequestTransportAroundDTO {

    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("long")
    private Double longitude;
    private Double radius;
    private String type;

}
