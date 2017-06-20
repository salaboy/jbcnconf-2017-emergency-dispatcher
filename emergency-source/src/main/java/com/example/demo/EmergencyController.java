package com.example.demo;

import com.example.demo.model.Emergency;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by msalatino on 05/06/2017.
 */
@RestController
@RequestMapping(value = "/api/")
@EnableBinding(Source.class)
public class EmergencyController {


    protected static Logger logger = LoggerFactory.getLogger(EmergencyController.class.getName());

    @Autowired
    private Source source;

    @PostMapping(value = "/emergency")
    public Emergency newEmergency(@RequestBody Emergency emergency) {
        String emergencyString = new Gson().toJson(emergency);
        logger.info("Emergency arrived: ", emergencyString);
        source.output().send(MessageBuilder.withPayload(emergencyString).build());
        return emergency;
    }
}
