package io.dkrcharlie.demo.zipkin.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RestController
public class SpringBootCustomerAuthorizationServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCustomerAuthorizationServiceApp.class,"--spring.application.name=customer-auth-service","--server.port=8004");
    }

    @GetMapping("/api/customer/{id}/auth")
    public String authCustomer(@PathVariable("id") Long id){
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // return true if id is odd number
        if((id.intValue() & 1) == 1) {
            return "true";
        } else {
            return "false";
        }
    }
}