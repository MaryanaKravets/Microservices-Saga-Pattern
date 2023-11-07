//package com.example.paymentservice.core.controller;
//
//import com.example.paymentservice.core.CreatePaymentDTO;
//import com.shared.core.command.ProcessPaymentCommand;
//import lombok.RequiredArgsConstructor;
//import org.axonframework.commandhandling.gateway.CommandGateway;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.UUID;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/payments")
//public class PaymentController {
//
//    private final CommandGateway commandGateway;
//
//    @PostMapping()
//    public ResponseEntity<String> saveProduct(@RequestBody CreatePaymentDTO createPaymentDTO){
//        ProcessPaymentCommand createProductCommand = ProcessPaymentCommand.builder()
//                .description(product.getDescription())
//                .name(product.getName())
//                .weight(product.getWeight())
//                .price(product.getPrice())
//                .productId(UUID.randomUUID().toString())
//                .build();
//
//        String returnValue;
//        try {
//            // send command to bus
//            returnValue = commandGateway.sendAndWait(createProductCommand);
//        } catch (Exception ex) {
//            returnValue = ex.getLocalizedMessage();
//        }
//        return ResponseEntity.ok(
//                returnValue
//        );
//    }
//
//}
