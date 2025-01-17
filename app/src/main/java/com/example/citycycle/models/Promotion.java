package com.example.citycycle.models;

public class Promotion {
    public int promotionId;
    public String title;
    public String description;
    public String startDate;
    public String endDate;

    public Promotion(int promotionId , String title, String description , String startDate,String endDate ){
        this.promotionId = promotionId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
