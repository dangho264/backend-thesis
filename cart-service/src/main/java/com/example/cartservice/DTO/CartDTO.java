package com.example.cartservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private String username;
    private String sellerName;
    private String name;
    private int productId;
    private String image_url;
    private BigDecimal price;
}
