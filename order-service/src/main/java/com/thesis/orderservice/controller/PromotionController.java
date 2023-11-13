package com.thesis.orderservice.controller;

import com.thesis.orderservice.dto.OrderRequest;
import com.thesis.orderservice.dto.PromotionDTO;
import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.entity.Promotion;
import com.thesis.orderservice.repository.PromotionRepository;
import com.thesis.orderservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/promotion")
@CrossOrigin
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private PromotionRepository promotionRepository;
    @PostMapping("/add-promotion")
    public void createOrder(@RequestBody PromotionDTO req) {
        promotionService.addPromotion(req);
    }

    @GetMapping
    public ResponseEntity<List<Promotion>> getListPromotion() {
        return ResponseEntity.ok(promotionRepository.findAll());
    }
    @GetMapping("/{couponCode}")
    public float validCouponCode(@PathVariable String couponCode) {
        return promotionService.validCouponCode(couponCode);
    }
}
