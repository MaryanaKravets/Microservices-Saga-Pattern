package com.example.sagaorderservice.core;

import com.example.sagacore.core.commands.ApproveOrderCommand;
import com.example.sagacore.core.commands.CreateOrderCommand;
import com.example.sagacore.core.commands.UpdateOrderStatusCommand;
import com.example.sagacore.core.events.OrderApprovedEvent;
import com.example.sagacore.core.events.OrderCreatedEvent;
import com.example.sagacore.core.events.OrderUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;

    private ItemType itemType;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        AggregateLifecycle.apply(OrderCreatedEvent.builder()
                        .orderId(createOrderCommand.getOrderId())
                        .itemType(createOrderCommand.getItemType())
                        .price(createOrderCommand.getPrice())
                .currency(createOrderCommand.getCurrency())
                .orderStatus(createOrderCommand.getOrderStatus())
                .build());
    }

    @CommandHandler
    protected void on(ApproveOrderCommand approveOrderCommand){
        AggregateLifecycle.apply(new OrderApprovedEvent(approveOrderCommand.orderId, com.example.sagacore.core.events.OrderStatus.APPROVED));
    }

    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.getOrderId();
        this.itemType = ItemType.valueOf(orderCreatedEvent.getItemType());
        this.price = orderCreatedEvent.getPrice();
        this.currency = orderCreatedEvent.getCurrency();
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.getOrderStatus());
    }

    @EventSourcingHandler
    protected void on(OrderApprovedEvent orderApprovedEvent){
        this.orderId = orderApprovedEvent.orderId;
        this.orderStatus = OrderStatus.APPROVED;
    }

    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent){
        this.orderId = orderUpdatedEvent.orderId;
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    }
}
