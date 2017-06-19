package com.example.demo;

import com.example.demo.model.Emergency;
import com.example.demo.model.Location;
import com.example.demo.model.Patient;
import com.netflix.appinfo.ApplicationInfoManager;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableDiscoveryClient
public class EmergencyProcedureDefaultApplication implements CommandLineRunner{

    @Autowired
    private ApplicationInfoManager appInfoManager;

    public static void main(String[] args) {
        SpringApplication.run(EmergencyProcedureDefaultApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("score", "40");
        metadata.put("type", "procedure");

        this.appInfoManager.registerAppMetadata(metadata);
    }

    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }
}

@RestController
@RequestMapping("/")
class ProcedureRestController{

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(value = "/procedure")
    public ResponseEntity<?> triggerProcedure(@RequestBody Emergency emergency) {

        registerListner();

        startProcess(emergency);

        return new ResponseEntity<>(HttpStatus
                .OK);
    }

    private void registerListner() {
        this.runtimeService.addEventListener(new ActivitiEventListener() {

            @Override
            public void onEvent(ActivitiEvent activitiEvent) {
//                source.output().send(MessageBuilder.withPayload(activitiEvent.toString()).build());
            }

            @Override
            public boolean isFailOnException() {
                return false;
            }
        });
    }

    private ProcessInstance startProcess(Emergency emergency) {
       return runtimeService.startProcessInstanceByKey("waiter", Collections.singletonMap("customerId", emergency.getId()));
    }
}
