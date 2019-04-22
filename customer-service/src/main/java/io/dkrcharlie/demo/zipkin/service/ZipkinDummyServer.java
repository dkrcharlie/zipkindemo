package io.dkrcharlie.demo.zipkin.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/zipkin/")
public class ZipkinDummyServer {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinDummyServer.class,"--spring.application.name=Zipkin-dummy-server","--server.port=9411");
    }

    @PostMapping("/api/v2/spans")
    public String span(@RequestBody String spanString){

        System.out.println(">>>>>>> spanString is : " + spanString);
        return "success";

    }

}
