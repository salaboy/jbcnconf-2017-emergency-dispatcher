package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by msalatino on 17/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleLocation {
    private Double lat;
    private Double lng;

    public GoogleLocation() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
