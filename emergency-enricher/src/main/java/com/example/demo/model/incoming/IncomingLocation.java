package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 15/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingLocation {
    private String description;


    public IncomingLocation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
