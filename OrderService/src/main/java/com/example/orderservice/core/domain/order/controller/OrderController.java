//package com.example.orderservice.core.domain.order.controller;
//
//import com.example.orderservice.core.domain.order.entity.OrderEntity;
//import com.example.orderservice.core.domain.order.service.OrderService;
//import com.example.orderservice.core.enums.OrderStatus;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/order")
//@RequiredArgsConstructor
//public class OrderController {
//
//    private final OrderService orderService;
//
//    @GetMapping
//    public ResponseEntity<List<OrderEntity>> getAllOrders() {
//
//        return ResponseEntity.ok(orderService.getAllOrders());
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<List<OrderEntity>> getUserOrders(@PathVariable(name = "userId") Long userId) {
//        return ResponseEntity.ok(orderService.getUserOrders(userId));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<OrderEntity> getOrderById(@PathVariable(name = "id") Long id) {
//        return ResponseEntity.ok(orderService.getOrderById(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteOrderById(@PathVariable(name = "id") Long id) {
//        orderService.deleteOrder(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Void> updateOrderStatus(@PathVariable(name = "id") Long id,
//                                                  @RequestParam(name = "status") String status) {
//        Optional<OrderStatus> optionalStatus = Arrays.stream(OrderStatus.values()).filter(v -> v.toString().equalsIgnoreCase(status)).findAny();
//        if (optionalStatus.isPresent()) {
//            orderService.changeOrderStatus(id, optionalStatus.get());
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest().build();
//        }
//    }
//
//    //TODO
//    @PostMapping
//    public ResponseEntity createOrder() {
//        return ResponseEntity.ok().build();
//    }
//}
