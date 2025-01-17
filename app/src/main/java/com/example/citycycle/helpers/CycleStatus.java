package com.example.citycycle.helpers;

public enum CycleStatus {
    AVAILABLE,
    NOT_AVAILABLE,
    MAINTENANCE;

    public static CycleStatus fromString(String status) {
        if (status == null) {
            throw new IllegalArgumentException("Cycle status cannot be null");
        }

        switch (status.toLowerCase()) {
            case "available":
                return AVAILABLE;
            case "not available":
            case "not_available":
                return NOT_AVAILABLE;
            case "maintenance":
                return MAINTENANCE;
            default:
                throw new IllegalArgumentException("Unknown cycle status: " + status);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case AVAILABLE:
                return "Available";
            case NOT_AVAILABLE:
                return "Not Available";
            case MAINTENANCE:
                return "Maintenance";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}

