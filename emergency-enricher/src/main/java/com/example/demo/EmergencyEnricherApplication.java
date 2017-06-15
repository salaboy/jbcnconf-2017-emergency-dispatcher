package com.example.demo;

import com.example.demo.model.Emergency;
import com.example.demo.model.EmergencyLocation;
import com.example.demo.model.EmergencyType;
import com.example.demo.model.incoming.IncomingEmergency;
import com.example.demo.model.Patient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
@EnableBinding(Processor.class)
public class EmergencyEnricherApplication {



    protected static Logger logger = LoggerFactory.getLogger(EmergencyEnricherApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(EmergencyEnricherApplication.class, args);
    }

    private RestTemplate restTemplate = restTemplate();

    private Gson gson = new Gson();

    @Transformer(inputChannel = Processor.INPUT,
            outputChannel = Processor.OUTPUT)
    public Object transform(String emergencyString) {

        logger.info("In Emergency Enricher");
        IncomingEmergency incomingEmergency = gson.fromJson(emergencyString, IncomingEmergency.class);
        //Get Patient By SSN
        Patient patient = queryBySSN(incomingEmergency.getSsn());

        //Decorate Emergency Code
        EmergencyType type = queryEmergencyCode(incomingEmergency.getCode());


        //Decorate Location
        EmergencyLocation location = decorateLocation(incomingEmergency.getLocation().getLatitude(),
                incomingEmergency.getLocation().getLongitude());

        Emergency emergency = new Emergency(UUID.randomUUID().toString(),patient, type, location);


        logger.info("Audit From Enricher: " + emergency.toString());

        return gson.toJson(emergency);
    }

    // Implement Location Decorator
    private EmergencyLocation decorateLocation(Long latitude, Long longitude) {
        //Default: if the emergency location cannot be decorated
        return new EmergencyLocation(latitude, longitude, "NA");
    }

    // Implement Emergency Code MicroService
    private EmergencyType queryEmergencyCode(String code) {
        //Default:  if the emergency code cannot be resolved
        return new EmergencyType(code, "NA");
    }

    private Patient queryBySSN(String ssn){
        String url = "http://localhost:{port}/patient/search/findBySsn?ssn="+ssn;
        int port = 8085;
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

    private RestTemplate restTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);
        return new RestTemplate(Arrays.asList(converter));
    }
}
