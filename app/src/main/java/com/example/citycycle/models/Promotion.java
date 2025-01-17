package com.example.citycycle.models;

import android.content.Context;

import com.example.citycycle.R;

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

    public int resourceFile(Context context) {
        // Use reflection to dynamically fetch the resource ID from R.drawable
        String imageName = this.image; // This is the image name (e.g., "my_image")
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return resId; // Returns the resource ID
    }
}
