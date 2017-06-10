package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;


@SpringBootApplication
@EnableBinding(Sink.class)
public class EmergencyDispatcherApplication {

    protected static Logger logger = LoggerFactory.getLogger(EmergencyDispatcherApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(EmergencyDispatcherApplication.class, args);
    }

    @StreamListener(Sink.INPUT)
    public void loggerSink(String emergency) {
        logger.info("Received: " + emergency + " now need to dispatch based on type");
        System.out.println("Received: " + emergency + " now need to dispatch based on type");
        logger.info("Audit From Dispatcher: " + emergency.toString());
    }


}
