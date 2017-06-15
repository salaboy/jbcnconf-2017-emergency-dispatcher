package com.example.demo.model;

/**
 * Created by msalatino on 15/06/2017.
 */

public class EmergencyLocation {
    private Long latitude;
    private Long longitude;
    private String description;

    public EmergencyLocation() {
    }

    public EmergencyLocation(Long latitude, Long longitude, String description) {
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

    @Override
    public String toString() {
        return "EmergencyLocation{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                '}';
    }
}
