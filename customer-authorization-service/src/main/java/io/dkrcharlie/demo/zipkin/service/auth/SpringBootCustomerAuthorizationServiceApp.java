package io.dkrcharlie.demo.zipkin.service.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class SpringBootCustomerAuthorizationServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCustomerAuthorizationServiceApp.class,args);
    }

    @GetMapping("/api/customer/{id}/auth")
    public Boolean authCustomer(@PathVariable("id") Long id){
        // return true if id is odd number
        return (id.intValue() & 1) == 1;
    }
}
