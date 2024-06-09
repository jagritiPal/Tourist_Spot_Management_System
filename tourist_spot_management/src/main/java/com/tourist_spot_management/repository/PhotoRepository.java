package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findBySpotId(Long spotId);
}
