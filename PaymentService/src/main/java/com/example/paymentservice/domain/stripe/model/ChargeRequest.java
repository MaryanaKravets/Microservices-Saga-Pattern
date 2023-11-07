package com.example.paymentservice.domain.stripe.model;

import lombok.Data;

@Data
public class ChargeRequest {

//    public enum Currency {
//        EUR, USD;
//    }
//    private String description;

    private String stripeToken;
    private int amount;
//    private Currency currency;
//    private String stripeEmail;
}
