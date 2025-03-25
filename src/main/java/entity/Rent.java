package entity;

public class Rent {
    //private long rentId;  //Нужно ли поле, если при хранении в мапе у объекста и так будет ключ?
    private long transportId;
    private long userId;
    private String timeStart;
    private String timeEnd;
    private double priceOfUnit;
    private String priceType;
    private double finalPrice;

    public Rent(long transportId, long userId, String timeStart, String timeEnd, double priceOfUnit, String priceType, double finalPrice) {
        this.transportId = transportId;
        this.userId = userId;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.priceOfUnit = priceOfUnit;
        this.priceType = priceType;
        this.finalPrice = finalPrice;
    }
}
