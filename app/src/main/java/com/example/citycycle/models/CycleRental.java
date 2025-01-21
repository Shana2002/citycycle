package com.example.citycycle.models;

public class CycleRental extends Cycle{
    public String endStation;
    public String startTime;
    public String endTime;
    public String paymentType;
    public int endStationId;
    public double cost;
    public double price;

    public CycleRental(Cycle cycle,String endStation,String startTime,String endTime){
        super(cycle.cycleId, cycle.title, cycle.description,
                cycle.type.toString(), cycle.station, cycle.status.toString(), cycle.images);
        this.endStation = endStation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.paymentType = paymentType;
    }

    public double CalculatePrice(){
        return  price;
    };

    public void setCost(double cost){
        this.cost = cost;
    }
}
