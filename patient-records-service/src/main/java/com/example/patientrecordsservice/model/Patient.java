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
    Long id;

    String ssn;

    String name;

    String surname;

    int age;

    public Patient() {
    }

    public Patient(String ssn, String name, String surname, int age) {
        this.ssn = ssn;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }
}
