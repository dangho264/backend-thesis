package com.thesis.orderservice.repository;


import com.thesis.orderservice.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    public boolean existsByCouponCode(String couponCode);
    public Promotion findPromotionByCouponCode(String couponCode);
}
