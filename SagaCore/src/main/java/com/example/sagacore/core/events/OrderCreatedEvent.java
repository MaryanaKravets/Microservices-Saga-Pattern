package com.example.sagacore.core.events;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderCreatedEvent {
    private final String orderId;
    private final String itemType;
    private final BigDecimal price;
    private final String currency;
    private final String orderStatus;
}
