package com.thesis.orderservice.repository;

import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.entity.OrderItem;
import com.thesis.orderservice.util.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByOrderStatusNotIn(List<OrderStatus> orderStatus);

    List<Order> findAllByUsername(String username);

    Page<Order> findAllByUsername(String username, Pageable pageable);
    Order findByOrderId(long id);
    Page<Order> findAllByOrderStatus(OrderStatus status, Pageable pageable);
}
