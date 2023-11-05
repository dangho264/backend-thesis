package com.example.cartservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@RedisHash("CartItem")
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int productId;
    private BigDecimal price;
    private String image_url;
    private String name;
    private int quantity;
    private String sellername;

}
