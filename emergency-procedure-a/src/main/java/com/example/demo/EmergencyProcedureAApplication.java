package com.example.demo;

import com.example.demo.model.Emergency;
import com.example.demo.model.Patient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.netflix.appinfo.ApplicationInfoManager;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding(Source.class)
@RestController
@RequestMapping(value = "/api/")
public class EmergencyProcedureAApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencyProcedureAApplication.class, args);
    }

    private RuntimeService runtimeService;

    private Source source;

    private ApplicationInfoManager appInfoManager;

    @Autowired
    public EmergencyProcedureAApplication(final RuntimeService runtimeService, ApplicationInfoManager appInfoManager, Source source) {
        this.source = source;
        this.runtimeService = runtimeService;
        this.appInfoManager = appInfoManager;


        Map<String, String> metadata = new HashMap<>();
        metadata.put("score", "40");
        metadata.put("type", "procedure");

        this.appInfoManager.registerAppMetadata(metadata);
    }


    @RequestMapping(value = "/procedure", method = RequestMethod.POST)
    public String triggerProcedure(@RequestBody Emergency emergency) {
        System.out.println("Emergency Procedure A: " + emergency);
        this.runtimeService.addEventListener(new ActivitiEventListener() {
            @Override
            public void onEvent(ActivitiEvent activitiEvent) {
                source.output().send(MessageBuilder.withPayload(activitiEvent.toString()).build());
            }

            @Override
            public boolean isFailOnException() {
                return false;
            }
        });
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("waiter", Collections.singletonMap("customerId", (Object) 243L));
        System.out.println("Process started: "+processInstance.getId());
        Gson gson = new Gson();
        String emergencyString = gson.toJson(emergency);
        return emergencyString;
    }

    @RequestMapping(value = "/procedure", method = RequestMethod.GET)
    public String helloFromProcedureA() {
        return "Hello From Procedure A";
    }



}
