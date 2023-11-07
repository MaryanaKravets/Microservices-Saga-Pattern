package com.example.orderservice.core.domain.order.repository;

import com.example.orderservice.core.domain.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserId(Long userId);
    Optional<OrderEntity> findByOrderId(String orderId);
}
