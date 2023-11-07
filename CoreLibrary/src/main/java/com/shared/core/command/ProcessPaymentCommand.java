package com.shared.core.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateVersion;

@Data
@Builder
public class ProcessPaymentCommand {

    @TargetAggregateVersion
    private final String orderId;
    private final String paymentId;
}
