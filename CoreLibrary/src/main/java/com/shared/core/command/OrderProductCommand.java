package com.shared.core.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class OrderProductCommand {
    @TargetAggregateIdentifier
    private String productId;
    private String orderId;
}
