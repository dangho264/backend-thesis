package com.thesis.productservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String image_url;
    @Column
    private String sellerName;
    @Column
    private int quantity;
    @Column
    private boolean status;
    @Column(name="unit_price")
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeProduct type;
    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<Artist> artists = new HashSet<>();
    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<Image> images = new HashSet<>();
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(price);
    }
}

