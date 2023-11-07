//package com.example.orderservice.core.domain.product.model;
//
//import com.example.orderservice.core.domain.order.entity.OrderEntity;
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Table(name = "products")
//@Entity
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String productId;
//
//    @Column(nullable = false)
//    private int quantity;
//
//    @ManyToMany
//    private List<OrderEntity> order = new ArrayList<>();
//}
