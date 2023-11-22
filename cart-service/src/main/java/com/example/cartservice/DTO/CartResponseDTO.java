package com.example.cartservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {
    private String username;
    private BigDecimal totalPrice;
//    private List<Map<String,List<CartItemResponseDTO>>> CartItem;
    private List<CartItemGroupedBySellerDTO> cartItemsBySeller;
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(totalPrice);
    }
}
