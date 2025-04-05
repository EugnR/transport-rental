package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Класс для аренды.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Rent extends BaseEntity {

    //TODO решить, что делать с полем
//    private long rentId; можно убрать тк в BaseEntity есть поле entityID?
    private long transportId;
    private long userId;
    //TODO решить в каком формате хранить время. ПОка что в тестах используется  ISO 8601 (YYYY-MM-DDTHH:MM:SSZ)
    private String timeStart;
    private String timeEnd;
    private double priceOfUnit;
    private String priceType;
    private double finalPrice;


}
