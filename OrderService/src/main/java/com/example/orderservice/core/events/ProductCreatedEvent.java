package com.example.orderservice.core.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductCreatedEvent {
    private String productId;
    private String name;
    private BigDecimal weight;
    private BigDecimal price;
    private int qty;
    private String description;
}
