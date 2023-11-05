package com.thesis.orderservice.service;

import com.thesis.orderservice.dto.OrderRequest;
import com.thesis.orderservice.entity.Order;
import com.thesis.orderservice.entity.OrderItem;
import com.thesis.orderservice.entity.PaymentMethod;
import com.thesis.orderservice.repository.OrderItemRepository;
import com.thesis.orderservice.repository.OrderRepository;
import com.thesis.orderservice.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private KafkaTemplate<String, String> toBasket;

    public List<Order> getListOrder() {
        return orderRepository.findAll();
    }


//    public OrderItem findOrderItemById(Set<OrderItem> orderItems, String targetId) {
//        for (OrderItem a :
//                orderItems) {
//            if (a.getName().equalsIgnoreCase(targetId)) {
//                return a;
//            }
//        } // Trả về phần tử có id khớp hoặc null nếu không tìm thấy
//        return null;
//    }

    public OrderItem findOrderItemById(Set<OrderItem> orderItems, int targetId) {
        for (OrderItem a :
                orderItems) {
            if (a.getId() == (targetId)) {
                return a;
            }
        } // Trả về phần tử có id khớp hoặc null nếu không tìm thấy
        return null;
    }

    public void addOrder(OrderRequest orderRequest) {
        Optional<PaymentMethod> paymentMethod = paymentMethodRepository.findById(orderRequest.getPaymentId());
        Order order = Order.builder()
                .email(orderRequest.getEmail())
                .phoneNumber(orderRequest.getPhoneNumber())
                .orderStatus("Pending")
                .firstName(orderRequest.getFirstName())
                .lastName(orderRequest.getLastName())
                .username(orderRequest.getUsername())
                .shippingAddress(orderRequest.getShippingAddress())
                .paymentStatus("Pending")
                .paymentMethod(paymentMethod.get())
                .totalPrice(orderRequest.getTotalPrice())
                .build();
        orderRepository.save(order);
        Set<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .sellername(item.getSellername())
                        .price(item.getPrice())
                        .note(item.getNote())
                        .orderId(order)
                        .build())
                .collect(Collectors.toSet());
        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);
        toBasket.send("cart-to-order", order.getUsername());
    }

//    public void updateOrder(OrderRequest orderRequest) {
//        Optional<Order> order = orderRepository.findByUsername(orderRequest.getUser());
//        if (order.isEmpty()) {
//            throw new AppException("Không tìm thấy hóa đơn", HttpStatus.NOT_FOUND);
//        }
//        Order order1 = order.get();
//        order1.setUsername(orderRequest.getUser());
//
//        orderRepository.save(order1);
//    }
//
//    public void deleteOrder(Integer id) {
//        Optional<Order> orderOptional = orderRepository.findById(id);
//        if (orderOptional.isEmpty()) {
//            throw new AppException("không tìm thấy hóa đơn", HttpStatus.NOT_FOUND);
//        }
//        orderRepository.delete(orderOptional.get());
//    }
    public List<Order> findOrderByUsername(String username){
        return orderRepository.findAllByUsername(username);
    }
    public void testKafka(String name) {
        toBasket.send("cart-order", name);
    }
}
