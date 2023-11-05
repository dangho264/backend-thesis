package com.thesis.orderservice.repository;

import com.thesis.orderservice.entity.OrderItem;
import com.thesis.orderservice.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
}
