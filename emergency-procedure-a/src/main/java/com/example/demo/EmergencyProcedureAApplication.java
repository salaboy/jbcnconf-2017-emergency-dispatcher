package com.example.demo;

import com.netflix.appinfo.ApplicationInfoManager;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@EnableBinding(Processor.class)
@EnableEurekaClient
public class EmergencyProcedureAApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencyProcedureAApplication.class, args);
    }

    private RuntimeService runtimeService;
    @Autowired
    private Source source;


    private ApplicationInfoManager appInfoManager;

    @Autowired
    public EmergencyProcedureAApplication(final RuntimeService runtimeService, ApplicationInfoManager appInfoManager) {
        this.runtimeService = runtimeService;
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
        Map<String, String> metadata = new HashMap<>();
        metadata.put("score", "40");
        metadata.put("type", "procedure");
        this.appInfoManager = appInfoManager;
        this.appInfoManager.registerAppMetadata(metadata);
    }




    @Transformer(inputChannel = Processor.INPUT,
            outputChannel = Processor.OUTPUT)
    public Object transform(String emergency) {
        System.out.println("Emergency Procedure A: " + emergency);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("waiter", Collections.singletonMap("customerId", (Object) 243L));
        System.out.println("Process started: "+processInstance.getId());

        return emergency;
    }
}
