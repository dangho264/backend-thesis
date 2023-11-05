package com.thesis.orderservice.controller;

import com.thesis.orderservice.dto.OrderRequest;
import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create-order")
    public void createOrder(@RequestBody OrderRequest req){
        orderService.addOrder(req);
    }

    @GetMapping
    public ResponseEntity<List<Order>> orderList(){
        return ResponseEntity.ok(orderService.getListOrder());
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<Order>> getOrderByUsername(@PathVariable(name="username") String username){
        return ResponseEntity.ok(orderService.findOrderByUsername(username));
    }
}
