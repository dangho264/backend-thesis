package com.thesis.productservice.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detail_product")
@Data
@Builder
public class DetailProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailId")
    private Integer detail_productId;
    @Column
    private String released;
    @Column
    private float weight;
    @Column
    private String dimensions;
    @OneToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
}
