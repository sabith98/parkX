package com.shatrend.parkx.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Parking implements Parcelable {
    private String id;
    private String email;
    private String password;
    private GeoPoint location;
    private String name;
    private double price;
    private List<Slot> slots;

    public Parking() {}

    public Parking(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    protected Parking(Parcel in) {
        id = in.readString();
        email = in.readString();
        password = in.readString();
//        location = in.readParcelable(GeoPoint.class.getClassLoader());
        name = in.readString();
        price = in.readDouble();
        slots = in.createTypedArrayList(Slot.CREATOR);
    }

    public static final Creator<Parking> CREATOR = new Creator<Parking>() {
        @Override
        public Parking createFromParcel(Parcel in) {
            return new Parking(in);
        }

        @Override
        public Parking[] newArray(int size) {
            return new Parking[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public static Parking fromDocumentSnapshot(DocumentSnapshot document) {
        Parking parking = new Parking();
        parking.setId(document.getId());
        parking.setLocation(document.getGeoPoint("location"));
        parking.setName(document.getString("name"));
        parking.setPrice(document.getDouble("price"));

        List<Map<String, Object>> slotsList = (List<Map<String, Object>>) document.get("slots");
        if (slotsList != null) {
            List<Slot> slots = new ArrayList<>();
            for (Map<String, Object> slotMap : slotsList) {
                String id = (String) slotMap.get("id");
                Boolean available = (Boolean) slotMap.get("available");
                String bookedBy = (String) slotMap.get("bookedBy");
                slots.add(new Slot(id, available, bookedBy));
            }
            parking.setSlots(slots);
        }

        return parking;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(password);
//        dest.writeParcelable(location, flags);
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeTypedList(slots);
    }
}
