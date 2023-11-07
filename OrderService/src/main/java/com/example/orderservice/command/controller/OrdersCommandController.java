package com.example.orderservice.command.controller;

import com.example.orderservice.command.CreateOrderCommand;
import com.example.orderservice.core.dto.CreateOrderRequest;
import com.example.orderservice.core.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersCommandController {

    private final CommandGateway commandGateway;

    @PostMapping
    public String createOrder(@RequestBody CreateOrderRequest dto) {
        CreateOrderCommand command = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .productId(dto.getProductId())
                .userId(UUID.randomUUID().toString())
                .quantity(dto.getQty())
                .addressId(dto.getAddressId())
                .orderStatus(OrderStatus.CREATED)
                .build();

        String obj;
        try {
            obj = commandGateway.sendAndWait(command);
        } catch (Exception ex) {
            obj = ex.getLocalizedMessage();
        }
        return obj;
    }
}
