package com.example.citycycle.models;

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
}
