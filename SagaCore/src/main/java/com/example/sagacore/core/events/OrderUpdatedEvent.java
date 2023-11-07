package com.example.sagacore.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderUpdatedEvent {
    public final String orderId;

    public final String orderStatus;

//    public OrderUpdatedEvent(String orderId, String orderStatus) {
//        this.orderId = orderId;
//        this.orderStatus = orderStatus;
//    }
}
