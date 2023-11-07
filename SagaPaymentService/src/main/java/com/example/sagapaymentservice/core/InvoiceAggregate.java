package com.example.sagapaymentservice.core;

import com.example.sagacore.core.commands.CreateInvoiceCommand;
import com.example.sagacore.core.events.InvoiceCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class InvoiceAggregate {
    @AggregateIdentifier
    private String paymentId;

    private String orderId;

    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) {
        log.info("InvoiceAggregate: catch CreateInvoiceCommand publish InvoiceCreatedEvent");
        AggregateLifecycle.apply(InvoiceCreatedEvent.builder()
                .paymentId(createInvoiceCommand.getPaymentId())
                .orderId(createInvoiceCommand.getOrderId())
                .build());
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent invoiceCreatedEvent) {
        log.info("InvoiceAggregate: update aggregate state from InvoiceCreatedEvent");
        this.paymentId = invoiceCreatedEvent.getPaymentId();
        this.orderId = invoiceCreatedEvent.getOrderId();
        this.invoiceStatus = InvoiceStatus.PAID;
    }
}
