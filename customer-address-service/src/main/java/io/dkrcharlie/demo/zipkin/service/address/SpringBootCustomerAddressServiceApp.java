package io.dkrcharlie.demo.zipkin.service.address;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@RestController
public class SpringBootCustomerAddressServiceApp {

    public static final String DUMMY_ADDRESS = "221B Baker Street";

    public static void main(String[] args) {
        run(SpringBootCustomerAddressServiceApp.class,args);
    }

    @GetMapping("/api/customer/{id}/address")
    public String getCustomerAddress(){
        return DUMMY_ADDRESS;
    }
}
