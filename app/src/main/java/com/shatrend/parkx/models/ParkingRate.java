package com.shatrend.parkx.models;

public class ParkingRate {
    private int parkingId;
    private int bikeRate, threeWheelerRate, fourWheelerRate;

    public ParkingRate(int parkingId, int bikeRate, int threeWheelerRate, int fourWheelerRate) {
        this.parkingId = parkingId;
        this.bikeRate = bikeRate;
        this.threeWheelerRate = threeWheelerRate;
        this.fourWheelerRate = fourWheelerRate;
    }

    public int getParkingId() {
        return parkingId;
    }

    public void setParkingId(int parkingId) {
        this.parkingId = parkingId;
    }

    public int getBikeRate() {
        return bikeRate;
    }

    public void setBikeRate(int bikeRate) {
        this.bikeRate = bikeRate;
    }

    public int getThreeWheelerRate() {
        return threeWheelerRate;
    }

    public void setThreeWheelerRate(int threeWheelerRate) {
        this.threeWheelerRate = threeWheelerRate;
    }

    public int getFourWheelerRate() {
        return fourWheelerRate;
    }

    public void setFourWheelerRate(int fourWheelerRate) {
        this.fourWheelerRate = fourWheelerRate;
    }
}
