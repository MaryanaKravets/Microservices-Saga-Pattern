package com.example.orderservice.core.model;

import com.example.orderservice.command.ApproveOrderCommand;
import com.example.orderservice.command.CreateOrderCommand;
import com.example.orderservice.command.RejectOrderCommand;
import com.example.orderservice.core.enums.OrderStatus;
import com.example.orderservice.core.events.OrderCreatedEvent;
import com.example.orderservice.core.events.OrderApprovedEvent;
import com.example.orderservice.core.events.OrderRejectedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate(snapshotTriggerDefinition = "orderSnapshotTriggerDefinition")
@NoArgsConstructor
@Slf4j
public class OrderAggregate {

    @AggregateIdentifier
    public String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        log.info("OrderAggregate: catch CreateOrderCommand and publish OrderCreatedEvent");
        OrderCreatedEvent event = new OrderCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(ApproveOrderCommand command) {
        log.info("OrderAggregate: catch ApproveOrderCommand and publish OrderApprovedEvent");
        OrderApprovedEvent event = new OrderApprovedEvent(command.getOrderId());
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RejectOrderCommand command) {
        log.info("OrderAggregate: catch RejectOrderCommand and publish OrderRejectedEvent");
        OrderRejectedEvent orderRejectedEvent = OrderRejectedEvent.builder()
                .orderId(command.getOrderId())
                        .reason(command.getReason())
                .build();
        AggregateLifecycle.apply(orderRejectedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        log.info("OrderAggregate: update aggregate state from OrderCreatedEvent");
        this.orderId = event.getOrderId();
        this.addressId = event.getAddressId();
        this.userId = event.getUserId();
        this.quantity = event.getQuantity();
        this.productId = event.getProductId();
        this.orderStatus = event.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent event) {
        log.info("OrderAggregate: update aggregate state from OrderApprovedEvent");
        this.orderStatus = event.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent event) {
        log.info("OrderAggregate: update aggregate state from OrderRejectedEvent");
        this.orderId = event.getOrderId();
        this.orderStatus = event.getOrderStatus();
    }
}
