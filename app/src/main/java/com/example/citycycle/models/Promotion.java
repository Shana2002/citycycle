package com.example.citycycle.models;

public class Promotion {
    public int promotionId;
    public String title;
    public String description;
    public String startDate;
    public String endDate;
    public String image;

    public Promotion(int promotionId , String title, String description , String startDate,String endDate ,String image){
        this.promotionId = promotionId;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.image = image;
    }
}
