package com.example.orderservice.query;

import com.example.orderservice.core.domain.order.entity.OrderEntity;
import com.example.orderservice.core.domain.order.repository.OrderRepository;
import com.example.orderservice.core.events.OrderApprovedEvent;
import com.example.orderservice.core.events.OrderCreatedEvent;
import com.example.orderservice.core.events.OrderRejectedEvent;
import com.example.orderservice.core.events.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventsHandler {

    private final OrderRepository orderRepository;

    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        log.info("OrderEventsHandler: catch OrderCreatedEvent and save order to read DB");
        OrderEntity order = new OrderEntity();
        BeanUtils.copyProperties(orderCreatedEvent, order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        log.info("OrderEventsHandler: catch OrderApprovedEvent and save approve order status to read DB");
        Optional<OrderEntity> optionalOrder = orderRepository.findByOrderId(orderApprovedEvent.getOrderId());
        if (optionalOrder.isPresent()) {
            optionalOrder.get().setOrderStatus(orderApprovedEvent.getOrderStatus());
            orderRepository.save(optionalOrder.get());
        }
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        log.info("OrderEventsHandler: catch OrderRejectedEvent and save reject order status to read DB");
        Optional<OrderEntity> optionalOrder = orderRepository.findByOrderId(orderRejectedEvent.getOrderId());
        if (optionalOrder.isPresent()) {
            optionalOrder.get().setOrderStatus(orderRejectedEvent.getOrderStatus());
            orderRepository.save(optionalOrder.get());
        }
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        log.info("&&&&& ProductCreatedEvent");
    }
}
