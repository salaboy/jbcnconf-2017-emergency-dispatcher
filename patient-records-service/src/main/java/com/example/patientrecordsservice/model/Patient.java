package com.example.patientrecordsservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by msalatino on 15/06/2017.
 */
@Entity
public class Patient {

    @Id
    @GeneratedValue
    private Long id;

    private String ssn;

    private String name;

    private String lastname;

    private Integer age;

    public Patient() {
    }

    public Patient(String ssn, String name, String lastname, Integer age) {
        this.ssn = ssn;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

    public String getSsn() {
        return ssn;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getAge() {
        return age;
    }
}
