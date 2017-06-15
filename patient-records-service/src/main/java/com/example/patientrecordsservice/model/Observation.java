package com.example.patientrecordsservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by msalatino on 15/06/2017.
 */
@Entity
public class Observation {

    @Id
    @GeneratedValue
    Long id;

    private String ssn;

    private String code;
    private String description;

    public Observation() {
    }

    public Observation(String ssn, String code, String description) {
        this.ssn = ssn;
        this.code = code;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
