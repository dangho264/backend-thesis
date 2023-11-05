package com.thesis.productservice.dto;

import com.thesis.productservice.entity.Artist;
import lombok.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private String productName;
    private String description;
    private String image_url;
    private String sellerName;
    private BigDecimal price;
    private int quantity;
    private int category_id;
    private int typeproduct_id;
    Set<String> artistName = new HashSet<>();
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(price);
    }
}
