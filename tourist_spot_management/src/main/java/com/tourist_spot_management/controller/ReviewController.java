package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.ReviewDTO;
import com.tourist_spot_management.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    //http://localhost:8080/api/reviews/add
    @PostMapping("/add")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/reviews
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    //http://localhost:8080/api/reviews/1
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        ReviewDTO review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    //http://localhost:8080/api/reviews/1
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long reviewId,
                                                  @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    //http://localhost:8080/api/reviews/1
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    //http://localhost:8080/api/reviews/spot/1
    @GetMapping("/spot/{spotId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsBySpotId(@PathVariable Long spotId) {
        List<ReviewDTO> reviewDTOList = reviewService.getReviewsBySpotId(spotId);
        return new ResponseEntity<>(reviewDTOList, HttpStatus.OK);
    }
}
