package com.example.orderservice.core.events;

import com.example.orderservice.core.enums.OrderStatus;
import lombok.Value;

@Value
public class OrderApprovedEvent {
    private String orderId;
    private OrderStatus orderStatus = OrderStatus.APPROVED;
}
