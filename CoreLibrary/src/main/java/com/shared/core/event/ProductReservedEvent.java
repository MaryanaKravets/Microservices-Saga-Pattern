package com.shared.core.event;

import lombok.Builder;
import lombok.Data;
import org.axonframework.eventsourcing.eventstore.jpa.DomainEventEntry;

@Data
@Builder
public class ProductReservedEvent {
    private final String orderId;
    private final String productId;
    private final int qty;
    private final String userId;
}
