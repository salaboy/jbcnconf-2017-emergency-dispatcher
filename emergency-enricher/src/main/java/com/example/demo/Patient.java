package com.example.demo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 05/06/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

    private String name;

    public Patient() {
    }

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                '}';
    }
}
