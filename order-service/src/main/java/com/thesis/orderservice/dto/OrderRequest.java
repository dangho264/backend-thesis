package com.thesis.orderservice.dto;

import com.thesis.orderservice.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String email;
    private String phoneNumber;
    private String username;
    private String sellername;
    private String shippingAddress;
    private String firstName;
    private String lastName;
//    private String note;
//    private float discount;
//    private int numberItem;
    private String paymentStatus;
//    private String couponCode;
    private String orderStatus;
    private long paymentId;
    Set<OrderItem> orderItems;
    private BigDecimal totalPrice;
//    private String rejectReason;
}
