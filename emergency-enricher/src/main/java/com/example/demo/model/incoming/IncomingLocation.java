package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 15/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingLocation {
    private Long latitude;
    private Long longitude;

    public IncomingLocation(Long latitude, Long longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
}
