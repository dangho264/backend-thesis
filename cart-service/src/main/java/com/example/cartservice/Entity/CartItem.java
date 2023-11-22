package com.example.cartservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Getter
@Setter
@RedisHash("CartItem")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int productId;
    private BigDecimal price;
    private String image_url;
    private String name;
    private int quantity;
    private String sellername;
    private boolean isSelected;
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(price);
    }
}
