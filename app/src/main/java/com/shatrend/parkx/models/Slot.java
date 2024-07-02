package com.shatrend.parkx.models;

public class Slot {
    private String id;
    private Boolean available;

    public Slot() {}

    public Slot(String id, Boolean available) {
        this.id = id;
        this.available = available;
    }

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
}
