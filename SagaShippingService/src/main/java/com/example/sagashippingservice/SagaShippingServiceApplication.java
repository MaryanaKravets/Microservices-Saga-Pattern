package com.example.sagashippingservice;

import com.example.sagashippingservice.core.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonXstreamConfig.class })
public class SagaShippingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SagaShippingServiceApplication.class, args);
    }

}
