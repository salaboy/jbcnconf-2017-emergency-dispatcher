package com.example.demo;

import com.example.demo.model.Emergency;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.stream.messaging.Source;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.support.MessageBuilder;
/**
 * Created by msalatino on 05/06/2017.
 */
@RestController
@RequestMapping(value = "/api/")
public class EmergencyController {

    @Autowired
    private Source source;


    @RequestMapping(value = "/emergency", method = RequestMethod.POST)
    public String newEmergency(@RequestBody Emergency emergency) {
        System.out.println("Emergency arrived: " + emergency);
        source.output().send(MessageBuilder.withPayload(emergency.toString()).build());
        return "OK";
    }
}
