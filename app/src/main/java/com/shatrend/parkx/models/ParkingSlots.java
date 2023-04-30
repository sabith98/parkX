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

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public int getBikeSlots() {
        return bikeSlots;
    }

    public void setBikeSlots(int bikeSlots) {
        this.bikeSlots = bikeSlots;
    }

    public int getThreeWheelerSlots() {
        return threeWheelerSlots;
    }

    public void setThreeWheelerSlots(int threeWheelerSlots) {
        this.threeWheelerSlots = threeWheelerSlots;
    }

    public int getFourWheelerSlots() {
        return fourWheelerSlots;
    }

    public void setFourWheelerSlots(int fourWheelerSlots) {
        this.fourWheelerSlots = fourWheelerSlots;
    }
}
