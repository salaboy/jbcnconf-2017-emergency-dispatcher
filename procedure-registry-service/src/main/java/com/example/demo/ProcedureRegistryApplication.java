package com.example.demo;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;


@EnableEurekaServer
@SpringBootApplication
public class ProcedureRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcedureRegistryApplication.class, args);
    }





}
