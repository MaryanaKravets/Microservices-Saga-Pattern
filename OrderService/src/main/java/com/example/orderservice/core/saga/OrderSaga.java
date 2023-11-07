package com.example.orderservice.core.saga;

import com.example.orderservice.command.ApproveOrderCommand;
import com.example.orderservice.command.RejectOrderCommand;
import com.example.orderservice.core.events.OrderApprovedEvent;
import com.example.orderservice.core.events.OrderCreatedEvent;
import com.example.orderservice.core.events.OrderRejectedEvent;
import com.shared.core.command.CancelProductReservationCommand;
import com.shared.core.command.ReserveProductCommand;
import com.shared.core.event.ProductReservationCancelledEvent;
import com.shared.core.event.ProductReservedEvent;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient DeadlineManager deadlineManager;

    private String scheduleId;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .qty(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();
        log.info("Saga 1: handle OrderCreatedEvent publish ReserveProductCommand for orderId " + orderCreatedEvent.getOrderId());

        commandGateway.send(reserveProductCommand, new CommandCallback<ReserveProductCommand, Object>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends ReserveProductCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {
                    log.info("Saga 1: error when publish command. Start compensating transaction " + commandMessage);
                    RejectOrderCommand rejectOrderCommand = RejectOrderCommand.builder()
                            .orderId(orderCreatedEvent.getOrderId())
                            .reason("error reason")
                            .build();
                    commandGateway.send(rejectOrderCommand);
                }
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        log.info("Saga 2: handle ProductReservedEvent publish CreateInvoiceCommand for orderId " + productReservedEvent.getOrderId());
        String paymentId = UUID.randomUUID().toString();
        try {
//            scheduleId = deadlineManager.schedule(Duration.of(10, ChronoUnit.SECONDS), "payment-processing-deadline", productReservedEvent);
//            if (true) return;
            commandGateway.sendAndWait(com.example.sagacore.core.commands.CreateInvoiceCommand.builder()
                    .paymentId(paymentId)
                    .orderId(productReservedEvent.getOrderId())
                    .build());
        } catch (Exception e) {
            log.info("Saga 2: exception when publish command. Start compensating transaction publish CancelProductReservationCommand");
            cancelProductReservation(productReservedEvent, e.getMessage());
        }
    }

    private void cancelProductReservation(ProductReservedEvent event, String reason) {
//        cancelDeadline();

        CancelProductReservationCommand command = CancelProductReservationCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .qty(event.getQty())
                .userId(event.getUserId())
                .reason(reason)
                .build();
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(com.example.sagacore.core.events.InvoiceCreatedEvent invoiceCreatedEvent) {
        log.info("Saga 3: catch InvoiceCreatedEvent publish CreateShippingCommand for orderId " + invoiceCreatedEvent.getOrderId());

//        cancelDeadline();

        String shippingId = UUID.randomUUID().toString();
        commandGateway.send(com.example.sagacore.core.commands.CreateShippingCommand.builder()
                .shippingId(shippingId)
                .orderId(invoiceCreatedEvent.getOrderId())
                .paymentId(invoiceCreatedEvent.getPaymentId())
                .build());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(com.example.sagacore.core.events.OrderShippedEvent orderShippedEvent) {
        log.info("Saga 4: catch OrderShippedEvent publish ApproveOrderCommand for orderId " + orderShippedEvent.getOrderId());
        commandGateway.send(ApproveOrderCommand.builder()
                .orderId(orderShippedEvent.getOrderId())
                .build());
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("Saga 5 end: lifecycle ends for order " + orderApprovedEvent.getOrderId());
    }


    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancelledEvent productReservationCancelledEvent) {
        log.info("Saga CT: catch ProductReservationCancelledEvent publish RejectOrderCommand for order " + productReservationCancelledEvent.getOrderId());
        RejectOrderCommand rejectOrderCommand = RejectOrderCommand.builder()
                .orderId(productReservationCancelledEvent.getOrderId())
                .reason(productReservationCancelledEvent.getReason())
                .build();
        commandGateway.send(rejectOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent orderRejectedEvent) {
        log.info("Order was successfully rejected " + orderRejectedEvent.getOrderId());
    }

    private void cancelDeadline() {
        if (Objects.nonNull(scheduleId)) {
            deadlineManager.cancelSchedule("payment-processing-deadline", scheduleId);
            scheduleId = null;
        }
    }

    @DeadlineHandler(deadlineName = "payment-processing-deadline")
    public void handlePaymentDeadline(ProductReservedEvent productReservedEvent) {
        log.info("Saga payment processing deadline. Start compensating transaction: cancel reserved product");
        cancelProductReservation(productReservedEvent, "Payment timeout");
    }
}
