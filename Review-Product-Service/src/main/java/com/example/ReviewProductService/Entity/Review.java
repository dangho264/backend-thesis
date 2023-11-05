package com.example.ReviewProductService.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
@Data
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int review_id;
    @Column(nullable = false,columnDefinition = "nvarchar")
    private String comment;
    @Column(nullable = false)
    private LocalDateTime commentDate;
    @Column(nullable = false)
    private String username;
    @Column
    private int productId;
    @OneToMany(mappedBy = "feedback")
    @JsonManagedReference
    private List<Feedback> feedbacks = new ArrayList<>();
}
