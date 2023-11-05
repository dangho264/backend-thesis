package com.example.ReviewProductService.Repository;

import com.example.ReviewProductService.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByProductId(int product_id);
}
