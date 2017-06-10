package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * Created by msalatino on 09/06/2017.
 */
@Component
public class AuditReciever {

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");

    }

}
