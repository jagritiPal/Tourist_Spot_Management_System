package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByLocation(String location);
    List<Spot> findBySpotName(String spotName);
}
