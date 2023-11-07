package com.example.sagacore.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.core.annotation.AliasFor;

public class UpdateOrderStatusCommand {
    @TargetAggregateIdentifier
    public final String orderId;
    public final String orderStatus;

    public UpdateOrderStatusCommand(String orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
