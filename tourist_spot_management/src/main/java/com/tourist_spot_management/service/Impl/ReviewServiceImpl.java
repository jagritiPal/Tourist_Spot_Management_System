package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Comment;
import com.tourist_spot_management.entity.Review;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.ReviewDTO;
import com.tourist_spot_management.repository.ReviewRepository;
import com.tourist_spot_management.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = mapToEntity(reviewDTO);
        Review savedReview = reviewRepository.save(review);
        return mapToDto(savedReview);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));
        return mapToDto(review);
    }

    @Override
    public ReviewDTO updateReview(Long reviewId, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId));

        existingReview.setUserId(reviewDTO.getUserId());
        existingReview.setRating(reviewDTO.getRating());
        existingReview.setFeedback(reviewDTO.getFeedback());
        existingReview.setReviewDate(reviewDTO.getReviewDate());

        Review updatedReview = reviewRepository.save(existingReview);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ResourceNotFoundException("Review not found with id: " + reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewDTO> getReviewsBySpotId(Long spotId) {
        List<Review> reviews = reviewRepository.findBySpotSpotId(spotId);
        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ReviewDTO mapToDto(Review review) {
        return modelMapper.map(review, ReviewDTO.class);
    }

    private Review mapToEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }
}
