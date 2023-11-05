package com.thesis.productservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPutDTO {
    private int id;
    private String productName;
    private String description;
    private String image_url;
    private String sellerName;
    private BigDecimal price;
    private int quantity;
    private int category_id;
    private int typeproduct_id;
    Set<String> artistName = new HashSet<>();
    List<String> comment = new ArrayList<>();
}
