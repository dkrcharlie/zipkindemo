package io.dkrcharlie.demo.zipkin.service;

import brave.Tracing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@RestController
public class SpringBootCustomerAddressServiceApp {

    public static final String ACTUAL_ADDRESS = "221B Baker Street";
    public static final String DUMMY_ADDRESS = "No where";

    @Autowired
    RestTemplate restTemplate;

    public static final String CUSTOMER_AUTH_WS_URL = "http://localhost:8004/api/customer/{id}/auth";

    public static void main(String[] args) {
        run(SpringBootCustomerAddressServiceApp.class,"--spring.application.name=customer-address-service","--server.port=8003");
    }

    @GetMapping("/api/customer/{id}/address")
    public String getCustomerAddress(@PathVariable("id") Long id){

        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String,Long> uriPara = new HashMap<>();
        uriPara.put("id",id);

        URI customerAuthWSURI = UriComponentsBuilder.fromUriString(CUSTOMER_AUTH_WS_URL)
                .buildAndExpand(uriPara)
                .toUri();

        String allowed = restTemplate.getForObject(customerAuthWSURI,String.class);
        if("true".equalsIgnoreCase(allowed)) {
            return ACTUAL_ADDRESS;
        } else {
            return DUMMY_ADDRESS;
        }

    }
}
