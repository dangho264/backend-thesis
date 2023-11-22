package com.example.cartservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponseDTO {
    private int id;
    private int productId;
    private BigDecimal price;
    private String image_url;
    private String name;
    private int quantity;
    private String sellerName;
    private boolean isSelected;
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(price);
    }
}
