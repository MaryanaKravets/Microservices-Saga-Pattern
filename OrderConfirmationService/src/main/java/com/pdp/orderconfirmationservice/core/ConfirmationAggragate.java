package com.pdp.orderconfirmationservice.core;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ConfirmationAggragate {

    @AggregateIdentifier
    private String orderId;

    private String message;

    public ConfirmationAggragate() {
    }

    @CommandHandler
    public ConfirmationAggragate(ConfirmOrderCommand confirmOrderCommand){
        System.out.println("publish confirm order event");
//        AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.getPaymentId(), createInvoiceCommand.getOrderId()));
        AggregateLifecycle.apply(OrderConfirmedEvent.builder()
                .orderId(confirmOrderCommand.getOrderId())
                .message(confirmOrderCommand.getMessage())
                .build());
    }

    @EventSourcingHandler
    protected void on(OrderConfirmedEvent orderConfirmedEvent){
        this.orderId = orderConfirmedEvent.getOrderId();
        this.message = orderConfirmedEvent.getMessage();
    }
}
