package com.shared.core.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CancelProductReservationCommand {

    @TargetAggregateIdentifier
    private final String productId;
    private final int qty;
    private final String orderId;
    private final String userId;
    private final String reason;
}
