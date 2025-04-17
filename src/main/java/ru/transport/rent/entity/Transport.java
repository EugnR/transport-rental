package ru.transport.rent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "transport")
public class Transport extends AbstractBaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "can_be_rented")
    private Boolean canBeRented;

    @Column(name = "transport_type")
    private String transportType;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "description")
    private String description;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "minute_price")
    private Double minutePrice;

    @Column(name = "day_price")
    private Double dayPrice;


}


