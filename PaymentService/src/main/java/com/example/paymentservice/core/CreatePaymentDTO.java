package com.example.paymentservice.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatePaymentDTO {

    private String userId;
    private String cardNumber;
    private String cvv;
    private int validUntilMonth;
    private int validUntilYear;
}
