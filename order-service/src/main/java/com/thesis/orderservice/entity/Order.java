package com.thesis.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thesis.orderservice.util.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String orderSku;
    private String email;
    private String phoneNumber;
    private String username;
    private String shippingAddress;
    private String firstName;
    private String lastName;
    private String note;
    //    private float tax;
    private float discount;
    private int numberItem;
    private LocalDateTime orderDate;
    private String couponCode;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private String paymentStatus;
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<OrderItem> orderItems;
    private String rejectReason;
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(totalPrice);
    }
}
