//package com.example.orderservice.core.domain.order.service.impl;
//
//import com.example.orderservice.core.domain.order.entity.OrderEntity;
//import com.example.orderservice.core.domain.order.repository.OrderRepository;
//import com.example.orderservice.core.domain.order.service.OrderService;
//import com.example.orderservice.core.enums.OrderStatus;
//import com.example.orderservice.core.exception.OrderNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//
//    private final OrderRepository orderRepository;
//
//    @Override
//    public List<OrderEntity> getAllOrders() {
//        return orderRepository.findAll();
//    }
//
//    @Override
//    public List<OrderEntity> getUserOrders(Long userId) {
//        return orderRepository.findByUserId(userId);
//    }
//
//    @Override
//    public OrderEntity getOrderById(Long id) {
//        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException());
//    }
//
//    @Override
//    public void deleteOrder(Long id) {
//        orderRepository.deleteById(id);
//    }
//
//    @Transactional
//    @Override
//    public void changeOrderStatus(Long id, OrderStatus status) {
//        OrderEntity order = orderRepository.findById(id).orElseThrow(OrderNotFoundException::new);
//        order.setOrderStatus(status);
//        orderRepository.save(order);
//    }
//
//    @Override
//    public void createOrder() {
//    //TODO
//    }
//}
