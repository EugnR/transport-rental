package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Класс для транспорта.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Transport extends BaseEntity {

    //TODO решить, что делать с полем
//    private long transportId; можно убрать тк в BaseEntity есть поле entityID?
    private long ownerId;
    private boolean canBeRented;
    private String transportType;
    private String model;
    private String color;
    private String identifier;
    private String description;
    private double latitude;
    private double longitude;
    private double minutePrice;
    private double dayPrice;


}


