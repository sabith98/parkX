package com.shatrend.parkx.models;

public class ParkingSlots {
    private int parkingId;
    private int bikeSlots;
    private int threeWheelerSlots;
    private int fourWheelerSlots;

    private int bikeFreeSlots;
    private int threeWheelerFreeSlots;
    private int fourWheelerFreeSlots;

//    public ParkingSlots() {
//    }

    public ParkingSlots(int parkingId, int bikeSlots, int threeWheelerSlots, int fourWheelerSlots) {
        this.parkingId = parkingId;
        this.bikeSlots = bikeSlots;
        this.threeWheelerSlots = threeWheelerSlots;
        this.fourWheelerSlots = fourWheelerSlots;

        this.bikeFreeSlots = bikeSlots;
        this.threeWheelerFreeSlots = threeWheelerSlots;
        this.fourWheelerFreeSlots = fourWheelerSlots;
    }

    public ParkingSlots(int parkingId, int bikeSlots, int threeWheelerSlots, int fourWheelerSlots, int bikeFreeSlots, int threeWheelerFreeSlots, int fourWheelerFreeSlots) {
        this.parkingId = parkingId;
        this.bikeSlots = bikeSlots;
        this.threeWheelerSlots = threeWheelerSlots;
        this.fourWheelerSlots = fourWheelerSlots;
        this.bikeFreeSlots = bikeFreeSlots;
        this.threeWheelerFreeSlots = threeWheelerFreeSlots;
        this.fourWheelerFreeSlots = fourWheelerFreeSlots;
    }

    //    public ParkingSlots(int bikeFreeSlots) {
//        this.bikeFreeSlots = bikeFreeSlots;
//    }
//
//    public ParkingSlots(int threeWheelerFreeSlots) {
//        this.threeWheelerFreeSlots = threeWheelerFreeSlots;
//    }

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


    public int getBikeFreeSlots() {
        return bikeFreeSlots;
    }

    public int getThreeWheelerFreeSlots() {
        return threeWheelerFreeSlots;
    }

    public int getFourWheelerFreeSlots() {
        return fourWheelerFreeSlots;
    }

}
