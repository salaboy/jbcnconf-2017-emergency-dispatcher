package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by msalatino on 05/06/2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Emergency {
    // Social Security Number
    private String ssn;
    private Location location;
    private Date date;
    private String code;

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "ssn='" + ssn + '\'' +
                ", location=" + location +
                ", date=" + date +
                ", code='" + code + '\'' +
                '}';
    }
}
