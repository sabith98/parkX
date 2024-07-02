package com.shatrend.parkx.models;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parking {
    private int id;
    private String email;
    private String password;

    private GeoPoint location;
    private String name;
    private double price;
    private List<Slot> slots;

    // Constructor
    public Parking() {
    }

    public Parking(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Getters and setters for Parking
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

    // Method to convert a DocumentSnapshot to a Parking object
    public static Parking fromDocumentSnapshot(DocumentSnapshot document) {
        Parking parking = new Parking();

        parking.setLocation(document.getGeoPoint("location"));
        parking.setName(document.getString("name"));
        parking.setPrice(document.getDouble("price"));

        // Convert the slots field from Firestore's List<Map<String, Object>> to our custom type
        List<Map<String, Object>> slotsList = (List<Map<String, Object>>) document.get("slots");
        if (slotsList != null) {
            List<Slot> slots = new ArrayList<>();
            for (Map<String, Object> slotMap : slotsList) {
                String id = (String) slotMap.get("id");
                Boolean available = (Boolean) slotMap.get("available");
                slots.add(new Slot(id, available));
            }
            parking.setSlots(slots);
        }

        return parking;
    }

}
