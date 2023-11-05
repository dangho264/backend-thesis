package com.example.cartservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductVm {
    private Integer id;
    private String name;
    private String description;
    private String image_url;
    private int quantity;
    private boolean status;
    private BigDecimal unitPrice;
}
