package com.example.patientrecordsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.stream.Stream;

@SpringBootApplication
public class PatientRecordsServiceApplication implements CommandLineRunner {

    @Autowired
    PatientRestResource patientRestResource;

    public static void main(String[] args) {
        SpringApplication.run(PatientRecordsServiceApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        Stream.of(new Patient("Mario", "Romano"),
                new Patient("Mauricio", "Saladino")).forEach(patient -> {
            patientRestResource.save(patient);
        });
    }
}

@RepositoryRestResource(collectionResourceRel = "patient", path = "patient")
interface PatientRestResource extends PagingAndSortingRepository<Patient, Long> {

}

@Entity
class Patient {

    @Id
    @GeneratedValue
    Long id;

    String name;

    String surname;

    public Patient() {
    }

    public Patient(String name, String surname) {
        this.name = name;
        this.surname = surname;
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
}

@RepositoryRestResource(collectionResourceRel = "observation", path = "observation")
interface ObservationRestResource extends PagingAndSortingRepository<Observation, Long> {

}

@Entity
class Observation {

    @Id
    @GeneratedValue
    Long id;


    public Observation() {
    }
}

@RepositoryRestResource(collectionResourceRel = "procedure", path = "procedure")
interface ProcedureRestResource extends PagingAndSortingRepository<Procedure, Long> {

}

@Entity
class Procedure {

    @Id
    @GeneratedValue
    Long id;

    public Procedure() {
    }
}

@RepositoryRestResource(collectionResourceRel = "emergency", path = "emergency")
interface EmergencyRestResource extends PagingAndSortingRepository<Emergency, Long> {

}

@Entity
class Emergency {

    @Id
    @GeneratedValue
    Long id;

    public Emergency() {
    }
}