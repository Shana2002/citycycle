package com.example.citycycle.models;

import com.example.citycycle.helpers.CycleStatus;

public class Cycle {
    public int cycleId;
    public String title;
    public String description;
    public String station;
    public CycleStatus status;
    public String[] images;

    public Cycle(int cycleId,String title ,String description,String station , CycleStatus status,String[] images){
        this.cycleId = cycleId;
        this.title = title;
        this.description = description;
        this.station = station;
        this.status = status;
        this.images = images;
    }
}
