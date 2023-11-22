package com.thesis.Email.Service.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailOrder {
    private String email;
    private String phoneNumber;
    private String shippingAddress;
    private String firstName;
    private String lastName;
    private BigDecimal totalPrice;
    private String paymentMethoid;
    Set<MailOrderItem> orderItems;
}
