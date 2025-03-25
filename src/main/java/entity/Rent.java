package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rent {

    private long rentId;
    private long transportId;
    private long userId;
    private String timeStart;
    private String timeEnd;
    private double priceOfUnit;
    private String priceType;
    private double finalPrice;


}
