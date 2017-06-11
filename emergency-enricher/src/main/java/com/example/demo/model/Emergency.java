package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 06/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Emergency {
    private String id;
    private Patient patient;

    public Emergency() {
    }

    public Emergency(String id, Patient patient) {
        this.id = id;
        this.patient = patient;
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
}
