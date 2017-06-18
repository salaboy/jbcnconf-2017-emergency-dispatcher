package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 17/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleGeometry {
    private GoogleLocation location;

    public GoogleLocation getLocation() {
        return location;
    }

    public void setLocation(GoogleLocation location) {
        this.location = location;
    }
}
