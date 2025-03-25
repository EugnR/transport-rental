package entity;

import lombok.Getter;

@Getter
public class Transport {
//    private long transportId; //Нужно ли поле, если при хранении в мапе у объекста и так будет ключ?
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

    public Transport(long ownerId, boolean canBeRented, String transportType, String model, String color, String identifier, String description, double latitude, double longitude, double minutePrice, double dayPrice) {
        this.ownerId = ownerId;
        this.canBeRented = canBeRented;
        this.transportType = transportType;
        this.model = model;
        this.color = color;
        this.identifier = identifier;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.minutePrice = minutePrice;
        this.dayPrice = dayPrice;
    }


}


