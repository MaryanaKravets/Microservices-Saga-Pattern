package com.pdp.orderconfirmationservice.core;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "confirmation")
@Data
public class ConfirmationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String message;
}
