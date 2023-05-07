package com.shatrend.parkx.models;

public class ParkingLocation {
    private int parkingId;
    private double latitude, longitude;

    public ParkingLocation(int parkingId, double latitude, double longitude) {
        this.parkingId = parkingId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getParkingId() {
        return parkingId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
