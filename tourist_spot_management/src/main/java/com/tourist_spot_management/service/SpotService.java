package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.PostDTO;
import com.tourist_spot_management.payload.SpotDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SpotService {

    SpotDTO createTourist(SpotDTO spotDTO, MultipartFile photo) throws IOException;

    List<SpotDTO> getAllTouristSpots();

    SpotDTO getTouristSpotById(Long spotId);

    SpotDTO updateTouristSpot(Long spotId, SpotDTO spotDTO, MultipartFile photo) throws IOException;

    void deleteTouristSpot(Long spotId);

    List<SpotDTO> searchByLocation(String location);

    List<SpotDTO> searchBySpotName(String spotName);

    SpotDTO getAllReviewsForParticularSpot(Long SpotId);
}
