package com.example.paymentservice.domain.stripe.controller;

import com.example.paymentservice.domain.stripe.model.ChargeRequest;
import com.example.paymentservice.domain.stripe.service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments/stripe")
public class StripeController {

    private final StripeService stripeService;

    @PostMapping("/charge")
    public ResponseEntity charge(@RequestBody ChargeRequest chargeRequest) {
        System.out.println("^^^^^ get request");

        return ResponseEntity.ok().build();
    }
}
