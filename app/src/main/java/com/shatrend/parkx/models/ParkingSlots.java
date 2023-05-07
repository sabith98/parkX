package com.shatrend.parkx.models;

public class ParkingSlots {
    private int parkingId;
    private int bikeSlots;
    private int threeWheelerSlots;
    private int fourWheelerSlots;

    public ParkingSlots(int parkingId, int bikeSlots, int threeWheelerSlots, int fourWheelerSlots) {
        this.parkingId = parkingId;
        this.bikeSlots = bikeSlots;
        this.threeWheelerSlots = threeWheelerSlots;
        this.fourWheelerSlots = fourWheelerSlots;
    }

    public int getParkingId() {
        return parkingId;
    }

    public int getBikeSlots() {
        return bikeSlots;
    }

    public int getThreeWheelerSlots() {
        return threeWheelerSlots;
    }

    public int getFourWheelerSlots() {
        return fourWheelerSlots;
    }

}
