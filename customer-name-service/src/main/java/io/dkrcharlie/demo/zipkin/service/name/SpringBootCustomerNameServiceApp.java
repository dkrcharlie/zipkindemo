package io.dkrcharlie.demo.zipkin.service.name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootCustomerNameServiceApp {

    public static final String DUMMY_CUSTOMER_NAME = "Sherlock Holmes";

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCustomerNameServiceApp.class,args);
    }

    @GetMapping("/api/customer/{id}/name")
    public String getCustomerName(@PathVariable("id") Long id){
        return DUMMY_CUSTOMER_NAME;
    }
}
