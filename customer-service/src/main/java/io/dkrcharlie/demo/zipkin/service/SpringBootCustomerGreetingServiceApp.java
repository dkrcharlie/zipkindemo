package io.dkrcharlie.demo.zipkin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
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

@SpringBootApplication
@RestController
@Slf4j
public class SpringBootCustomerGreetingServiceApp {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static final String CUSTOMER_NAME_WS_URL = "http://localhost:8002/api/customer/{id}/name";
    public static final String CUSTOMER_ADDRESS_WS_URL = "http://localhost:8003/api/customer/{id}/address";

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCustomerGreetingServiceApp.class,"--spring.application.name=customer-greeting-service","--server.port=8001");
    }

    @GetMapping("/api/customer/{id}/greeting")
    public String greeting(@PathVariable("id") String id){

        Map<String,String> uriPara = new HashMap<String,String>();

        uriPara.put("id",id);

        URI customerNameWSURI = UriComponentsBuilder.fromUriString(CUSTOMER_NAME_WS_URL)
                .buildAndExpand(uriPara)
                .toUri();
        URI customerAddressWSURI = UriComponentsBuilder.fromUriString(CUSTOMER_ADDRESS_WS_URL)
                .buildAndExpand(uriPara)
                .toUri();

        String name = restTemplate().getForObject(customerNameWSURI,String.class);
        String address = restTemplate().getForObject(customerAddressWSURI,String.class);

        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Hello, " + name + " from " + address;
    }
}
