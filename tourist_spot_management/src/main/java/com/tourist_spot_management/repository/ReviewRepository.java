package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Comment;
import com.tourist_spot_management.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySpotSpotId(Long spotId);
}
