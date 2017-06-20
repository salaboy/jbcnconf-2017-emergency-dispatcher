package com.example.demo;

import com.example.demo.model.Emergency;
import com.example.demo.model.EmergencyLocation;
import com.example.demo.model.EmergencyType;
import com.example.demo.model.Patient;
import com.example.demo.model.incoming.GoogleResponse;
import com.example.demo.model.incoming.IncomingEmergency;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Transformer;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;


@SpringBootApplication
@EnableBinding(Processor.class)
@EnableEurekaClient
public class EmergencyEnricherApplication {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }

    protected static Logger logger = LoggerFactory.getLogger(EmergencyEnricherApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(EmergencyEnricherApplication.class, args);
    }


    @Transformer(inputChannel = Processor.INPUT,
            outputChannel = Processor.OUTPUT)
    public Object transform(String emergencyString) {

        logger.info("In Emergency Enricher");
        IncomingEmergency incomingEmergency = new Gson().fromJson(emergencyString, IncomingEmergency.class);

        Emergency emergency = new Emergency(UUID.randomUUID().toString())
                .addLocation(retriveLocation(incomingEmergency.getLocation().getDescription()))
                .addPatient(retrivePatient(incomingEmergency.getSsn()))
                .addType(retiveEmergencyCode(incomingEmergency.getCode()));


        logger.info("Audit From Enricher: " + emergency.toString());

        return new Gson().toJson(emergency);
    }

    // Implement Location Decorator
    private EmergencyLocation retriveLocation(String description) {
        String emergencyDescriptionDistilled = description.replaceAll(" ", "+");
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + emergencyDescriptionDistilled + "&sensor=true";
        GoogleResponse responseEntity = restTemplate.getForObject(url, GoogleResponse.class);


        logger.info("address Resources: " + responseEntity.getResults().length);
        return new EmergencyLocation(responseEntity.getResults()[0].getGeometry().getLocation().getLat(),
                responseEntity.getResults()[0].getGeometry().getLocation().getLng(),
                responseEntity.getResults()[0].getFormattedAddress());


    }

    // Implement Emergency Code MicroService
    private EmergencyType retiveEmergencyCode(String code) {
        //Default:  if the emergency code cannot be resolved

        return new EmergencyType(code, "NA");
    }

    private Patient retrivePatient(String ssn) {
        String url = "http://localhost:8080/patient-record-service/patient/search/findBySsn?ssn=" + ssn;
        ResponseEntity<Patient> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, null, Patient.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            logger.info("Request OK.. looking for resources..." + responseEntity.getStatusCode());
            Patient patient = responseEntity.getBody();
            logger.info("Patient Resources: " + patient);
            return patient;
        }
        return null;
    }
}
