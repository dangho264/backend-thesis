package com.thesis.orderservice.repository;

import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUsername(String username);
}
