package com.thesis.productservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
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
    @Column(name="createdAt")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeProduct type;
    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
//    @JsonBackReference
    private Set<Artist> artists = new HashSet<>();
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<Image> images = new HashSet<>();
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private DetailProduct detailProduct;
    public String getFormattedPrice() {
        DecimalFormat format = new DecimalFormat("#,###.##");
        return format.format(price);
    }
}

