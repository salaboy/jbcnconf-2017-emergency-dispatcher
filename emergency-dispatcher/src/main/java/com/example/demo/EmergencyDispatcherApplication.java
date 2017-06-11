package com.example.demo;

import com.example.demo.model.Emergency;
import com.example.demo.model.Patient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@SpringBootApplication
@EnableBinding(Sink.class)
@EnableEurekaClient
public class EmergencyDispatcherApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

    protected static Logger logger = LoggerFactory.getLogger(EmergencyDispatcherApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(EmergencyDispatcherApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void dispatchToProcedure(String emergency) {
        logger.info("Received: " + emergency + " now need to dispatch based on type");
        System.out.println("Received: " + emergency + " now need to dispatch based on type");
        logger.info("Audit From Dispatcher: " + emergency.toString());

        System.out.println("Get Procedures: ");

        List<String> services = discoveryClient.getServices();
        for(String s : services){
            System.out.println("\t > Service: " + s);
            List<ServiceInstance> instances = discoveryClient.getInstances(s);
            URI selectedUri = null;
            for(ServiceInstance instance : instances){
                System.out.println("Filtering by producedures");
                if(instance.getMetadata().containsKey("type") && instance.getMetadata().get("type").equals("procedure") ){

                    System.out.println("Service Instance Id: " + instance.getServiceId());
                    System.out.println("Service Instance MetaData: " + instance.getMetadata());
                    System.out.println("Service Instance Host: " + instance.getHost());
                    System.out.println("Service Instance Port: " + instance.getPort());
                    System.out.println("Service Instance URI: " + instance.getUri());
                    if(instance.getServiceId().equalsIgnoreCase("procedure-a")){
                        selectedUri = instance.getUri();
                    }
                }
            }
            if(selectedUri != null) {
                System.out.println("Selected URI = " + selectedUri);
                RestTemplate restTemplate = restTemplate();
                String url = selectedUri + "/api/procedure";
                //@TODO: Parse incoming string and create new emergency here
                HttpEntity<Emergency> request = new HttpEntity<>(new Emergency(UUID.randomUUID().toString(),
                        new Patient("salaboy")));
                ResponseEntity<Emergency> responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST, request, Emergency.class);


                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Response from procedure: " + responseEntity);
                } else {
                    System.out.println("Error calling procedure");
                }
            }
        }
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
