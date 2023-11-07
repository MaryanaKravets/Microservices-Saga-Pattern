package com.example.sagacore.core.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateShippingCommand {
    @TargetAggregateIdentifier
    private final String shippingId;
    private final String orderId;
    private final String paymentId;
}
