package com.tourist_spot_management.repository;

import com.tourist_spot_management.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findBySpotId(Long spotId);
}
