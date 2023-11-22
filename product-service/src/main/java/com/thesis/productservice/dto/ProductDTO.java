package com.thesis.productservice.dto;

import com.thesis.productservice.entity.Artist;
import lombok.*;

import javax.persistence.Column;
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
    private String released;
    private float weight;
    private String dimensions;
    List<String> artistName = new ArrayList<>();
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(price);
    }
}
