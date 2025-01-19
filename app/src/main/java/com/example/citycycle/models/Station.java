package com.example.citycycle.models;

public class Station {
    public int stationId ;
    public String stationName;
    public String location;

    public Station(int stationId , String stationName,String location){
        this.stationId = stationId;
        this.stationName = stationName;
        this.location = location;
    }
}
