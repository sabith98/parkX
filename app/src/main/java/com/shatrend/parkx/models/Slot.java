package com.shatrend.parkx.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Slot implements Parcelable {
    private String id;
    private Boolean available;
    private String bookedBy;

    public Slot() {}

    public Slot(String id, Boolean available, String bookedBy) {
        this.id = id;
        this.available = available;
        this.bookedBy = bookedBy;
    }

    protected Slot(Parcel in) {
        id = in.readString();
        byte tmpAvailable = in.readByte();
        available = tmpAvailable == 0 ? null : tmpAvailable == 1;
        bookedBy = in.readString();
    }

    public static final Creator<Slot> CREATOR = new Creator<Slot>() {
        @Override
        public Slot createFromParcel(Parcel in) {
            return new Slot(in);
        }

        @Override
        public Slot[] newArray(int size) {
            return new Slot[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (available == null ? 0 : available ? 1 : 2));
        dest.writeString(bookedBy);
    }
}
