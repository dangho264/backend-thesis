package com.thesis.productservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private int image_id;
    @Column(nullable = false,columnDefinition = "nvarchar")
    private String image_url;
    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonManagedReference
    private Product product;
}