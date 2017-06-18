package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Created by msalatino on 17/06/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleAddress {
    @JsonProperty("formatted_address")
    private String formattedAddress;
    @JsonProperty("geometry")
    private GoogleGeometry geometry;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public GoogleGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(GoogleGeometry geometry) {
        this.geometry = geometry;
    }
}
