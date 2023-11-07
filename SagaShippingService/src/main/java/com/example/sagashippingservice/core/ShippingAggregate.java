package com.example.sagashippingservice.core;

import com.example.sagacore.core.commands.CreateShippingCommand;
import com.example.sagacore.core.events.OrderShippedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;
    private String orderId;
    private String paymentId;

    public ShippingAggregate() {
    }

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand) {
        log.info("ShippingAggregate: catch CreateShippingCommand and  publish OrderShippedEvent");
        AggregateLifecycle.apply(OrderShippedEvent.builder()
                .shippingId(createShippingCommand.getShippingId())
                .orderId(createShippingCommand.getOrderId())
                .paymentId(createShippingCommand.getPaymentId())
                .build());
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent) {
        log.info("ShippingAggregate: update aggregate state from OrderShippedEvent");
        this.shippingId = orderShippedEvent.getShippingId();
        this.orderId = orderShippedEvent.getOrderId();
    }
}
