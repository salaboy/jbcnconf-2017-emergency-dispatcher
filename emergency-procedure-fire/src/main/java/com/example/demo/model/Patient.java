package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by msalatino on 11/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient implements Serializable {
    private String ssn;
    private String name;

    public Patient() {
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
