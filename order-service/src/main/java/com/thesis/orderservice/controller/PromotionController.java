package com.thesis.orderservice.controller;

import com.thesis.orderservice.dto.OrderRequest;
import com.thesis.orderservice.dto.PromotionDTO;
import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.entity.Promotion;
import com.thesis.orderservice.repository.PromotionRepository;
import com.thesis.orderservice.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/promotion")
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
    public double validCouponCode(@PathVariable String couponCode) {
        return promotionService.validCouponCode(couponCode);
    }
    @GetMapping("/getPromotion")
    public Page<Promotion> getPromotionByUsername(@RequestParam(name = "page") int page,
                                                  @RequestParam(name = "size") int size,
                                                  @RequestParam(name = "username") String username) {
        Pageable pageable = PageRequest.of(page, size);
        return promotionService.getPromotionByUsername(username, pageable);
    }
}
