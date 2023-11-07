package com.example.sagacore.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceCreatedEvent {
    private final String paymentId;
    private final String orderId;
}
