package com.example.ReviewProductService.Service;

import com.example.ReviewProductService.DTO.ReviewDTO;
import com.example.ReviewProductService.Entity.Review;
import com.example.ReviewProductService.Repository.FeedbackRepository;
import com.example.ReviewProductService.Repository.ReviewRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;

    public long addComment(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setComment(reviewDTO.getComment());
        review.setUsername(reviewDTO.getUsername());
        review.setCommentDate(LocalDateTime.now());
        WebClient webClient = WebClient.create("http://localhost:9004/product");
        ResponseEntity<Boolean> response = webClient.get().uri(String.format("/isExist/%d",reviewDTO.getProduct_id())).retrieve().toEntity(Boolean.class).block();
        boolean isExist = response.getBody();

        if (!isExist) {
            throw new ResourceNotFoundException("Product ID không tồn tại");
        }
        else {
            review.setProductId(reviewDTO.getProduct_id());
        }
        reviewRepository.save(review);
        return review.getReview_id();
    }
//    public Review getReviewById(int id){
//        return reviewRepository.findByReview_id(id);
//    }
    public List<Review> getReviewByProductId(int id){
        return reviewRepository.findByProductId(id);
    }
}
