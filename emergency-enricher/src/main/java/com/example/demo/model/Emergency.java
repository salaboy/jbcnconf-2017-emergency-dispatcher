package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 06/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Emergency {
    private String id;
    private Patient patient;
    private EmergencyType type;
    private EmergencyLocation location;

    public Emergency() {
    }

    public Emergency(String id) {
        this.id = id;
    }

    public Emergency(String id, Patient patient, EmergencyType type, EmergencyLocation location) {
        this.id = id;
        this.patient = patient;
        this.type = type;
        this.location = location;
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

    public Emergency addPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public EmergencyType getType() {
        return type;
    }

    public Emergency addType(EmergencyType type) {
        this.type = type;
        return this;
    }

    public Emergency addLocation(EmergencyLocation location) {
        this.location = location;
        return this;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "id='" + id + '\'' +
                ", patient=" + patient +
                ", type=" + type +
                ", location=" + location +
                '}';
    }
}
