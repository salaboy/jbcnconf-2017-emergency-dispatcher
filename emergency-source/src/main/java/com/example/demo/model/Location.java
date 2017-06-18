package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 05/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String description;

    public Location() {
    }

    public Location(String description) {
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
