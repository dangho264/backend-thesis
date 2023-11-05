package com.example.ReviewProductService.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "feedback")
@Data
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int feedback_id;
    @Column(nullable = false,columnDefinition = "nvarchar")
    private String feedback;
    @Column(nullable = false)
    private LocalDateTime feedbackDate;
    @Column(nullable = false)
    private String username;
    @ManyToOne
    @JoinColumn(name="review_id")
    @JsonManagedReference
    private Review review;
}
