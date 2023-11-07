package com.example.paymentservice.core.event;

import com.example.paymentservice.core.repository.PaymentRepository;
import com.example.paymentservice.domain.payment.PaymentEntity;
import com.shared.core.event.PaymentProcessedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventHandler {

    private final PaymentRepository paymentRepository;

    @EventHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        System.out.println("PaymentEventHandler: catch PaymentProcessedEvent and save to DB");
        PaymentEntity payment = new PaymentEntity();
        BeanUtils.copyProperties(paymentProcessedEvent, payment);
        paymentRepository.save(payment);
    }
}
