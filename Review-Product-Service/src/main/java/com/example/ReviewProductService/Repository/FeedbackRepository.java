package com.example.ReviewProductService.Repository;

import com.example.ReviewProductService.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
}
