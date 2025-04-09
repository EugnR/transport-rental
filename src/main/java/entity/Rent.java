package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Класс для аренды.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Rent extends AbstractBaseEntity {

    private Long transportId;
    private Long userId;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private Double priceOfUnit;
    private String priceType;
    private Double finalPrice;

}
