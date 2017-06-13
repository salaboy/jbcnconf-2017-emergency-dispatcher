package com.example.demo;

import com.example.demo.model.Emergency;
import com.example.demo.model.Patient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

    private Gson gson = new Gson();

    /*
     * In order to dispatch an Emergency to a specific procedure we need to:
      *  1) calculate the "score" of the emergency based on the emergency properties
     *  2) Look for all the available procedures
     *  3) Match the procedure defined score with the calculated score
     *  4) Pick the procedure that fits better based on the score
     *  5) if there is no procedure that matches with the calculated score we should use the default procedure if it is available
     */
    @StreamListener(Sink.INPUT)
    public void dispatchToProcedure(String emergencyJson) {
        logger.info("Received: " + emergencyJson + " now need to dispatch based on type");
        System.out.println("Received: " + emergencyJson + " now need to dispatch based on type");
        logger.info("Audit From Dispatcher: " + emergencyJson.toString());

        Emergency emergency = gson.fromJson(emergencyJson, Emergency.class);

        Integer score  = calculateScore(emergency);

        System.out.println("Emergency Score: " + score);

        List<String> services = discoveryClient.getServices();
        System.out.println("Get Procedures: ");
        for(String s : services){
            List<ServiceInstance> instances = discoveryClient.getInstances(s);
            URI selectedUri = null;
            URI defaultUri = null;
            for(ServiceInstance instance : instances){
                if(instance.getMetadata().containsKey("type") && instance.getMetadata().get("type").equals("procedure") ){
                    System.out.println("Service Instance Id: " + instance.getServiceId());
                    System.out.println("Service Instance MetaData: " + instance.getMetadata());
                    System.out.println("Service Instance Host: " + instance.getHost());
                    System.out.println("Service Instance Port: " + instance.getPort());
                    System.out.println("Service Instance URI: " + instance.getUri());
                    if(instance.getServiceId().equalsIgnoreCase("procedure-default")){
                        defaultUri = instance.getUri();
                    }
                    int procedureScoreValue = 0;
                    if(instance.getMetadata().containsKey("score")) {
                        String procedureScore = instance.getMetadata().get("score");
                        if(procedureScore != null && !procedureScore.isEmpty()){
                            procedureScoreValue = Integer.parseInt(procedureScore);
                        }
                        if(score == procedureScoreValue) {
                            selectedUri = instance.getUri();
                        }
                    }
                }
            }
            if(selectedUri == null && defaultUri != null){
                selectedUri = defaultUri;
            }
            if(selectedUri != null) {
                System.out.println("Selected URI = " + selectedUri);
                RestTemplate restTemplate = restTemplate();
                String url = selectedUri + "/api/procedure";
                HttpEntity<Emergency> request = new HttpEntity<>(emergency);
                ResponseEntity<Emergency> responseEntity = restTemplate.exchange(url,
                        HttpMethod.POST, request, Emergency.class);

                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    System.out.println("Response from procedure: " + responseEntity);
                } else {
                    System.out.println("Error calling procedure");
                }
            }else{
                System.out.println("There is no procedure available at this time");
            }

        }
    }

    /*
     * this calculate the score of the emergency based on the internal values
     */
    private Integer calculateScore(Emergency emergency) {

        return 0;
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
