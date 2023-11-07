package com.example.sagaorderservice;

import com.example.sagaorderservice.core.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonXstreamConfig.class })
public class SagaOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaOrderServiceApplication.class, args);
	}

}
