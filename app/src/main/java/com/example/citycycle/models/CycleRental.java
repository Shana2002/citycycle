package com.example.citycycle.models;

public class CycleRental extends Cycle{
    public String endStation;
    public String startTime;
    public String endTime;
    public String paymentType;
    public double cost;
    public double price;

    public CycleRental(int cycleId,String title ,String description,String type,String station , String status,String[] images,String endStation,String startTime,String endTime){
        super(cycleId,title,description,type,station,status,images);
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
