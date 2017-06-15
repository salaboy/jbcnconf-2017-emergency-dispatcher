package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 06/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Emergency {
    private String id;
    private Patient patient;
    private Location location;
    private EmergencyType type;

    public Emergency() {
    }


    public Emergency(String id, Patient patient, Location location, EmergencyType type) {
        this.id = id;
        this.patient = patient;
        this.location = location;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public EmergencyType getType() {
        return type;
    }

    public void setType(EmergencyType type) {
        this.type = type;
    }
}
