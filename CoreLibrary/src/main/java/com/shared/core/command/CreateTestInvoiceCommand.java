package com.shared.core.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateTestInvoiceCommand {
    @TargetAggregateIdentifier
    private final String paymentId;

    private final String orderId;

}
