package com.example.ReviewProductService.Controller;

import com.example.ReviewProductService.DTO.ReviewDTO;
import com.example.ReviewProductService.Entity.Review;
import com.example.ReviewProductService.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping("/add-comment")
    public ResponseEntity<Long> addComment(@RequestBody ReviewDTO reviewDTO){
        long id = reviewService.addComment(reviewDTO);
        return ResponseEntity.ok(id);
    }
//    @GetMapping("/{id}")
//    public Review getReviewById(@PathVariable() int id){
//        return reviewService.getReviewById(id);
//    }
    @GetMapping("/{id}")
    public List<Review> getReviewByProductId(@PathVariable() int id){
        return reviewService.getReviewByProductId(id);
    }
}
