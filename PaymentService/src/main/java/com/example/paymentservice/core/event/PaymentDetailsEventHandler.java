package com.example.paymentservice.core.event;

import com.shared.core.model.PaymentDetails;
import com.shared.core.query.FetchUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsEventHandler {

    @QueryHandler
    public PaymentDetails queryHandler(FetchUserPaymentDetailsQuery query) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("Maryana Kravets")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();
        return paymentDetails;
    }
}
