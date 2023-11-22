package com.thesis.orderservice.repository;


import com.thesis.orderservice.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    public boolean existsByCouponCode(String couponCode);
    public Promotion findPromotionByCouponCode(String couponCode);
    public Page<Promotion> findAllBySellerName(String username, Pageable pageable);
}
