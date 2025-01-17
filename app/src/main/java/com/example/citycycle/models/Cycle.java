package com.example.citycycle.models;

import android.content.Context;

import com.example.citycycle.helpers.BikeType;
import com.example.citycycle.helpers.CycleStatus;

public class Cycle {
    public int cycleId;
    public String title;
    public String description;
    public BikeType type;
    public String station;
    public CycleStatus status;
    public String[] images;

    public Cycle(int cycleId,String title ,String description,String type,String station , String status,String[] images){
        this.cycleId = cycleId;
        this.title = title;
        this.description = description;
        this.station = station;
        this.images = images;
        this.type = BikeType.fromString(type);
        this.status = CycleStatus.fromString(status);
    }

    public int resourceFile(Context context) {
        // Use reflection to dynamically fetch the resource ID from R.drawable
        String imageName = this.images[0]; // This is the image name (e.g., "my_image")
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return resId; // Returns the resource ID
    }
}
