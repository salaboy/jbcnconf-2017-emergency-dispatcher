package com.example.demo;

import com.example.demo.model.Emergency;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;


@SpringBootApplication
@EnableBinding(Sink.class)
@EnableEurekaClient
public class EmergencyDispatcherApplication {

    @Autowired
    private DiscoveryClient discoveryClient;

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

    protected static Logger logger = LoggerFactory.getLogger(EmergencyDispatcherApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(EmergencyDispatcherApplication.class, args);
    }

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
        logger.info("Audit From Dispatcher", emergencyJson.toString());

        Emergency emergency = new Gson().fromJson(emergencyJson, Emergency.class);

        Integer score = calculateScore(emergency);

        URI selectedUri = getServiceFromEmergencyScore(score);

        if(selectedUri != null){
            ResponseEntity<String> responseEntity = callEmergencyProcedure(emergency, selectedUri);
            logger.info("Response", responseEntity.getStatusCode());
        }else{
            logger.info("No procedure registered");
        }

    }

    private ResponseEntity<String> callEmergencyProcedure(Emergency emergency, URI selectedUri) {
        HttpEntity<Emergency> request = new HttpEntity<>(emergency);
        return restTemplate.exchange(selectedUri + "/procedure", HttpMethod.POST, request, String.class);
    }

    private URI getServiceFromEmergencyScore(Integer score) {
        List<String> services = discoveryClient.getServices();
        URI defaultUri = null;

        for (String s : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(s);

            for (ServiceInstance instance : instances) {
                if (instance.getMetadata().containsKey("type") && instance.getMetadata().get("type").equals("procedure")) {
                    if (instance.getServiceId().equalsIgnoreCase("procedure-default")) {
                        defaultUri = instance.getUri();
                    }

                    if (instance.getMetadata().containsKey("score")) {
                        int procedureScoreValue = 0;
                        String procedureScore = instance.getMetadata().get("score");
                        if (procedureScore != null && !procedureScore.isEmpty()) {
                            procedureScoreValue = Integer.parseInt(procedureScore);
                        }
                        if (score == procedureScoreValue) {
                            return instance.getUri();
                        }
                    }
                }
            }
        }
        return defaultUri;
    }

    /*
     * this calculate the score of the emergency based on the internal values
     */
    private Integer calculateScore(Emergency emergency) {
        if (emergency.getType().getCode().equalsIgnoreCase("FIRE")) {
            return 40;
        }

        return 0;
    }
}
