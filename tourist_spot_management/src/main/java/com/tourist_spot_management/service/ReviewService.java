package com.tourist_spot_management.service;


import com.tourist_spot_management.payload.CommentDTO;
import com.tourist_spot_management.payload.ReviewDTO;

import java.util.List;

public interface ReviewService {

    ReviewDTO createReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getAllReviews();

    ReviewDTO getReviewById(Long reviewId);

    ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO);

    void deleteReview(Long reviewId);

    List<ReviewDTO> getReviewsBySpotId(Long spotId);
}
