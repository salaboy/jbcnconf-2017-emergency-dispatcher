package com.example.demo.model.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by msalatino on 15/06/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingEmergency {
    private String ssn;
    private String code;
    private Date date;
    private IncomingLocation location;

    public IncomingEmergency() {
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public IncomingLocation getLocation() {
        return location;
    }

    public void setLocation(IncomingLocation location) {
        this.location = location;
    }
}
