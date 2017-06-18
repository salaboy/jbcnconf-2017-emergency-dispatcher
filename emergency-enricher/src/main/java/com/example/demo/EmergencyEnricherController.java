package com.example.demo;

import com.example.demo.model.EmergencyLocation;
import com.example.demo.model.incoming.GoogleResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by msalatino on 18/06/2017.
 */
@RestController
@RequestMapping(value = "/api/")
public class EmergencyEnricherController {
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/enricher", method = RequestMethod.GET)
    public String decorateLocation(@RequestParam("loc") String locationString){
        if(locationString==null || locationString.isEmpty()){
            throw new IllegalStateException("locationString cannot be null or empty");
        }
        String emergencyDescriptionDistilled = locationString.replaceAll(" ", "+");
        String url = "http://maps.googleapis.com/maps/api/geocode/json?address=" + emergencyDescriptionDistilled + "&sensor=true";
        GoogleResponse responseEntity = restTemplate.getForObject(url, GoogleResponse.class);


        return new EmergencyLocation(responseEntity.getResults()[0].getGeometry().getLocation().getLat(),
                responseEntity.getResults()[0].getGeometry().getLocation().getLng(),
                responseEntity.getResults()[0].getFormattedAddress()).toString();

    }
}
