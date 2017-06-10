package com.example.demo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@EnableBinding(Processor.class)
public class EmergencyEnricherApplication {



    protected static Logger logger = LoggerFactory.getLogger(EmergencyEnricherApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(EmergencyEnricherApplication.class, args);
    }



    @Transformer(inputChannel = Processor.INPUT,
            outputChannel = Processor.OUTPUT)
    public Object transform(String emergency) {

        logger.info("IN the Pre-Processor - ");
        RestTemplate restTemplate = restTemplate();
        String url = "http://localhost:{port}/patient?page={page}&size={size}";
        int port = 8085;
        ResponseEntity<PagedResources<Patient>> responseEntity = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedResources<Patient>>() {},
                port, 0, 20);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            logger.info("Request OK.. looking for resources..." + responseEntity.getStatusCode());

            PagedResources<Patient> patientResources = responseEntity.getBody();
            logger.info("Patient Resources: " + patientResources);
            List<Patient> patients = new ArrayList(patientResources.getContent());
            logger.info("Patients size: " + patients.size());
            for(Patient p : patients) {
                logger.info("Patient p: " + p);

            }
        } else{
            logger.error("Error: " + responseEntity.getStatusCode());
        }

        logger.info("Audit From Enricher: " + emergency.toString());

        return emergency;
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
