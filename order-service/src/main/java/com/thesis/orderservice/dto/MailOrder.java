package com.thesis.orderservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailOrder {
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private String paymentMethod;
    Set<MailOrderItem> orderItems;
}
