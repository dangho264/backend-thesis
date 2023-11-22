package com.example.cartservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemGroupedBySellerDTO {
    private String sellerName;
    private List<CartItemResponseDTO> cartItems;
}
