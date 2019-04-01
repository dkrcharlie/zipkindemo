package io.dkrcharlie.demo.zipkin.service.address;

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

import static org.springframework.boot.SpringApplication.*;

@SpringBootApplication
@RestController
public class SpringBootCustomerAddressServiceApp {

    public static final String ACTUAL_ADDRESS = "221B Baker Street";
    public static final String DUMMY_ADDRESS = "No where";


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static final String CUSTOMER_AUTH_WS_URL = "http://localhost:8004/api/customer/{id}/auth";

    public static void main(String[] args) {
        run(SpringBootCustomerAddressServiceApp.class,args);
    }

    @GetMapping("/api/customer/{id}/address")
    public String getCustomerAddress(@PathVariable("id") Long id){

        Map<String,Long> uriPara = new HashMap<>();
        uriPara.put("id",id);

        URI customerAuthWSURI = UriComponentsBuilder.fromUriString(CUSTOMER_AUTH_WS_URL)
                .buildAndExpand(uriPara)
                .toUri();

        Boolean allowed = restTemplate().getForObject(customerAuthWSURI,Boolean.class);
        if(allowed) {
            return ACTUAL_ADDRESS;
        } else {
            return DUMMY_ADDRESS;
        }

    }
}
