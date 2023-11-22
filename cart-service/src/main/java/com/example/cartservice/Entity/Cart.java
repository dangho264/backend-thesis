package com.example.cartservice.Entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RedisHash("Cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    @Id
    private String id; // Đây là username
//    private String username;
    private Long orderId;
    private BigDecimal totalPrice;
    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<CartItem> cartItems = new HashSet<>();
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(totalPrice);
    }
}
