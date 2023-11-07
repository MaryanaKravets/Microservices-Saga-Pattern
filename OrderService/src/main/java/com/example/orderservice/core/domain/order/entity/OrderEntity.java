package com.example.orderservice.core.domain.order.entity;


import com.example.orderservice.core.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "orders")
@Entity
public class OrderEntity {

    @Id
    @Column(unique = true)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderId;
    private String userId;
    private BigDecimal price;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private int quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String productId;
}
