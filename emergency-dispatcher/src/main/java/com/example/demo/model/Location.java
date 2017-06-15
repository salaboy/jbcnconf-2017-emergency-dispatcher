package com.example.demo.model;

/**
 * Created by msalatino on 15/06/2017.
 */
public class Location {
    private Long latitude;
    private Long longitude;
    private String description;

    public Location() {
    }

    public Location(Long latitude, Long longitude, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
