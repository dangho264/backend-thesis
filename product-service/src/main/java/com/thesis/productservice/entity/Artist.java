package com.thesis.productservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private int artist_id;
    @Column(nullable = false,columnDefinition = "nvarchar")
    private String artistName;
//    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
