package com.example.citycycle.helpers;

public enum BikeType {
    ROAD_BIKE("Road Bike"),
    CITY_BIKE("City Bike"),
    SINGLE_GEAR_BIKE("Single Gear Bike"),
    E_BIKE("Electric Bike"),
    LADIES_BIKE("Ladies Bike");

    private final String displayName;

    BikeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static BikeType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Bike type cannot be null");
        }

        switch (type.toLowerCase()) {
            case "road bike":
                return ROAD_BIKE;
            case "city bike":
                return CITY_BIKE;
            case "single gear bike":
                return SINGLE_GEAR_BIKE;
            case "electric bike":
            case "e bike":
                return E_BIKE;
            case "ladies bike":
                return LADIES_BIKE;
            default:
                throw new IllegalArgumentException("Unknown bike type: " + type);
        }
    }
}
