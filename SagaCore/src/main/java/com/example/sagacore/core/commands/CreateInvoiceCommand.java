package com.example.sagacore.core.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateInvoiceCommand {
    @TargetAggregateIdentifier
    private final String paymentId;

    private final String orderId;
}
