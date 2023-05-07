package com.shatrend.parkx.models;

public class ParkingRate {
    private int parkingId;
    private int bikeRate = 0, threeWheelerRate = 0, fourWheelerRate = 0;

    public ParkingRate(int parkingId, int bikeRate, int threeWheelerRate, int fourWheelerRate) {
        this.parkingId = parkingId;
        this.bikeRate = bikeRate;
        this.threeWheelerRate = threeWheelerRate;
        this.fourWheelerRate = fourWheelerRate;
    }

    public int getParkingId() {
        return parkingId;
    }

    public int getBikeRate() {
        return bikeRate;
    }

    public int getThreeWheelerRate() {
        return threeWheelerRate;
    }

    public int getFourWheelerRate() {
        return fourWheelerRate;
    }

}
