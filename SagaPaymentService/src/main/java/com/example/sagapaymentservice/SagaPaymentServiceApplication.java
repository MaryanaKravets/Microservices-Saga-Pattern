package com.example.sagapaymentservice;

import com.example.sagapaymentservice.core.AxonXstreamConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ AxonXstreamConfig.class })
public class SagaPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaPaymentServiceApplication.class, args);
	}

}
