package com.example.sagacore.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShippedEvent {
    private final String shippingId;
    private final String orderId;
    private final String paymentId;
}
