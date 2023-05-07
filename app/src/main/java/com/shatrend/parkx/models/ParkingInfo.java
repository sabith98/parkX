package com.shatrend.parkx.models;

public class ParkingInfo {
    private int parkingId;
    private String parkingName, parkingPhone;
    private byte[] parkingImage;

    public ParkingInfo(int parkingId, String parkingName, String parkingPhone, byte[] parkingImage) {
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.parkingPhone = parkingPhone;
        this.parkingImage = parkingImage;
    }

    public int getParkingId() {
        return parkingId;
    }

    public String getParkingName() {
        return parkingName;
    }

    public String getParkingPhone() {
        return parkingPhone;
    }

    public byte[] getParkingImage() {
        return parkingImage;
    }

}
