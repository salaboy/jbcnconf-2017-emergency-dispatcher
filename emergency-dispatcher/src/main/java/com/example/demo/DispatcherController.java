package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by msalatino on 05/06/2017.
 */
@RestController
@RequestMapping(value = "/api/")
public class DispatcherController {

    @Autowired
    private DiscoveryClient discoveryClient;

    protected static Logger logger = LoggerFactory.getLogger(DispatcherController.class.getName());

    @RequestMapping(value = "/dispatcher", method = RequestMethod.GET)
    public String getProcedures() {
        System.out.println("Get Procedures: ");

        List<String> services = discoveryClient.getServices();
        for(String s : services){
            List<ServiceInstance> instances = discoveryClient.getInstances(s);
            for(ServiceInstance instance : instances){
                System.out.println("Filtering by producedures");
                if(instance.getMetadata().containsKey("type") && instance.getMetadata().get("type").equals("procedure") ){
                    System.out.println("Service Instance Id: " + instance.getServiceId());
                    System.out.println("Service Instance MetaData: " + instance.getMetadata());
                }
            }
        }

        return "OK";
    }
}
