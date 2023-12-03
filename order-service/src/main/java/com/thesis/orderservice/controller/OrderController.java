package com.thesis.orderservice.controller;

import com.thesis.orderservice.dto.OrderRequest;
import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/create-order")
    public List<Long> createOrder(@RequestBody OrderRequest req){
       return orderService.addOrder(req);
    }

    @GetMapping
    public ResponseEntity<List<Order>> orderList(){
        return ResponseEntity.ok(orderService.getListOrder());
    }
    @GetMapping("/paging")
    public ResponseEntity<Page<Order>> getOrderPaging(@RequestParam(name = "page") int page,
                                                      @RequestParam(name = "size") int size,
                                                      @RequestParam(name = "username") String username){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getOrderByUsername(username,pageable));
    }
    @GetMapping("/{username}")
    public ResponseEntity<List<Order>> getOrderByUsername(@PathVariable(name="username") String username){
        return ResponseEntity.ok(orderService.findOrderByUsername(username));
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(orderService.findOrderById(id));
    }
    @GetMapping("/revenue/{username}")
    public ResponseEntity<Map<String, BigDecimal>> getRevenueByMonth(@PathVariable(name="username") String username){
        return ResponseEntity.ok(orderService.calculateRevenueByMonth(username));
    }
    @GetMapping("/revenue-pending/{username}")
    public ResponseEntity<Map<String, BigDecimal>> getRevenuePendingByMonth(@PathVariable(name="username") String username){
        return ResponseEntity.ok(orderService.calculateRevenuePendingByMonth(username));
    }
    @GetMapping("/revenue-product/")
    public ResponseEntity<BigDecimal> getRevenuePendingByproduct(@RequestParam(name = "username") String username,
                                                                 @RequestParam(name = "productId") int productId){
        return ResponseEntity.ok(orderService.calculateProductRevenue(productId,username));
    }
    @GetMapping("/status/")
    public Page<Order> getOrderByStatus(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "size") int size,
                                        @RequestParam(name = "status") int status){
        Pageable pageable = PageRequest.of(page, size);
        return orderService.getListOrderByStatus(status, pageable);
    }
    @PutMapping("/{id}/{state}")
    public ResponseEntity updateStatusOrder(@PathVariable int id, @PathVariable int state){
        orderService.updateStatusOrder(id, state);
        return ResponseEntity.ok("Cập nhật trạng thái đơn hàng thành công");
    }
}
