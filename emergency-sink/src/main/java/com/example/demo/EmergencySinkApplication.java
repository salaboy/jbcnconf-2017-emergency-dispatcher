package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;



@SpringBootApplication
@EnableBinding(Sink.class)
public class EmergencySinkApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmergencySinkApplication.class, args);
    }


    @StreamListener(Sink.INPUT)
    public void loggerSink(String date) {
        System.out.println("Received: " + date);
    }
}
