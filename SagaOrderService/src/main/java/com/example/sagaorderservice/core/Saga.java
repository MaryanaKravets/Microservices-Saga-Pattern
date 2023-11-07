package com.example.sagaorderservice.core;

import com.example.sagacore.core.commands.ApproveOrderCommand;
import com.example.sagacore.core.commands.CreateInvoiceCommand;
import com.example.sagacore.core.commands.CreateShippingCommand;
import com.example.sagacore.core.commands.UpdateOrderStatusCommand;
import com.example.sagacore.core.events.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;
import java.util.UUID;

@org.axonframework.spring.stereotype.Saga
public class Saga {


        @Inject
//        @Autowired
        private transient CommandGateway commandGateway;

        @StartSaga
        @SagaEventHandler(associationProperty = "orderId")
        public void handle(OrderCreatedEvent orderCreatedEvent){
            String paymentId = UUID.randomUUID().toString();
            System.out.println("Saga invoked");

            //associate Saga
            SagaLifecycle.associateWith("paymentId", paymentId);

            System.out.println("order id" + orderCreatedEvent.getOrderId());

            //send the commands
//            commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.getOrderId()));
            commandGateway.send(CreateInvoiceCommand.builder()
                    .paymentId(paymentId)
                    .orderId(orderCreatedEvent.getOrderId())
                    .build());
        }

        @SagaEventHandler(associationProperty = "paymentId")
        public void handle(InvoiceCreatedEvent invoiceCreatedEvent){
            String shippingId = UUID.randomUUID().toString();

            System.out.println("Saga continued InvoiceCreatedEvent");

            //associate Saga with shipping
           // SagaLifecycle.associateWith("shipping", shippingId);

            //send the create shipping command
//            commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.getOrderId(), invoiceCreatedEvent.getPaymentId()));
            commandGateway.send(CreateShippingCommand.builder()
                    .shippingId(shippingId)
                    .orderId(invoiceCreatedEvent.getOrderId())
                    .paymentId(invoiceCreatedEvent.getPaymentId())
                    .build());
//            SagaLifecycle.end();
        }

        @SagaEventHandler(associationProperty = "orderId")
        public void handle(OrderShippedEvent orderShippedEvent){
            System.out.println("Saga continued 222 OrderShippedEvent");
//            SagaLifecycle.end();
//            commandGateway.send(ApproveOrderCommand.builder()
//                    .orderId(orderShippedEvent.getOrderId())
//                    .build());
            SagaLifecycle.associateWith("orderId", orderShippedEvent.getOrderId());
            commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.getOrderId(), String.valueOf(OrderStatus.SHIPPED)));
        }
//
        @SagaEventHandler(associationProperty = "orderId")
        public void handle(OrderUpdatedEvent orderUpdatedEvent){
            System.out.println("Saga continued ++ finish OrderShippedEvent");
            commandGateway.send(new ApproveOrderCommand(orderUpdatedEvent.orderId));
            //SagaLifecycle.end();
        }

//    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        System.out.println("Saga 4 end: lifecycle ends for order " + orderApprovedEvent.orderId);
    }

}
