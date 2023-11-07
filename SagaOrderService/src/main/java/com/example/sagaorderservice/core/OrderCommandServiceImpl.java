package com.example.sagaorderservice.core;

import com.example.sagacore.core.commands.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderCommandServiceImpl implements OrderCommandService{
    private final CommandGateway commandGateway;

    public OrderCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
        return commandGateway.send(CreateOrderCommand.builder()
                        .orderId(UUID.randomUUID().toString())
                        .itemType(orderCreateDTO.getItemType())
                        .price(orderCreateDTO.getPrice())
                .currency(orderCreateDTO.getCurrency())
                .orderStatus(String.valueOf(OrderStatus.CREATED))
                .build());
    }
}
