package com.example.demo.model;

/**
 * Created by msalatino on 15/06/2017.
 */
public class Location {
    private Double latitude;
    private Double longitude;
    private String description;

    public Location() {
    }

    public Location(Double latitude, Double longitude, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
