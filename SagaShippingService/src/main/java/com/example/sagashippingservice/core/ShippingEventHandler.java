package com.example.sagashippingservice.core;

import com.example.sagacore.core.events.OrderShippedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShippingEventHandler {

    private final ShippingRepository shippingRepository;

    @EventHandler
    public void on(OrderShippedEvent orderShippedEvent) {
        log.info("ShippingEventHandler: handle OrderShippedEvent and save shipping to read DB");
        ShippingEntity shippingEntity = new ShippingEntity();
        shippingEntity.setShippingId(orderShippedEvent.getShippingId());
        shippingEntity.setOrderId(orderShippedEvent.getOrderId());
        shippingEntity.setPaymentId(orderShippedEvent.getPaymentId());
        shippingRepository.save(shippingEntity);
    }
}
