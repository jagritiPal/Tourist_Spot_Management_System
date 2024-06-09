package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Review;
import com.tourist_spot_management.entity.Spot;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.ReviewDTO;
import com.tourist_spot_management.payload.SpotDTO;
import com.tourist_spot_management.repository.SpotRepository;
import com.tourist_spot_management.service.SpotService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SpotDTO createTourist(SpotDTO spotDTO, MultipartFile photo) throws IOException {
        Spot spot = mapToEntity(spotDTO);
        if (photo != null && !photo.isEmpty()) {
            spot.setPhoto(photo.getBytes());
        }
        Spot savedSpot = spotRepository.save(spot);
        return mapToDto(savedSpot);
    }

    @Override
    public List<SpotDTO> getAllTouristSpots() {
        List<Spot> spots = spotRepository.findAll();
        return spots.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpotDTO getTouristSpotById(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new ResourceNotFoundException("Spot not found with id: " + spotId));
        return mapToDto(spot);
    }

    @Override
    public SpotDTO updateTouristSpot(Long spotId, SpotDTO spotDTO, MultipartFile photo) throws IOException {
        Spot existingSpot = spotRepository.findById(spotId)
                .orElseThrow(() -> new ResourceNotFoundException("Spot not found with id: " + spotId));

        existingSpot.setSpotName(spotDTO.getSpotName());
        existingSpot.setDescription(spotDTO.getDescription());
        existingSpot.setLocation(spotDTO.getLocation());
        existingSpot.setHistory(spotDTO.getHistory());
        existingSpot.setOpeningHours(spotDTO.getOpeningHours());
        existingSpot.setEntryFee(spotDTO.getEntryFee());
        if (photo != null && !photo.isEmpty()) {
            existingSpot.setPhoto(photo.getBytes());
        }

        Spot updatedSpot = spotRepository.save(existingSpot);
        return mapToDto(updatedSpot);
    }

    @Override
    public void deleteTouristSpot(Long spotId) {
        if (!spotRepository.existsById(spotId)) {
            throw new ResourceNotFoundException("Spot not found with id: " + spotId);
        }
        spotRepository.deleteById(spotId);
    }

    @Override
    public List<SpotDTO> searchByLocation(String location) {
        List<Spot> spots = spotRepository.findByLocation(location);
        return spots.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SpotDTO> searchBySpotName(String spotName) {
        List<Spot> spots = spotRepository.findBySpotName(spotName);
        return spots.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpotDTO getAllReviewsForParticularSpot(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new ResourceNotFoundException("Spot not found with id: " + spotId));

        List<ReviewDTO> reviewsDTOs = spot.getReviews().stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());

        SpotDTO spotDTO = mapToDto(spot);
        spotDTO.setReviews(reviewsDTOs);

        return spotDTO;
    }

    private SpotDTO mapToDto(Spot spot) {
        SpotDTO dto = modelMapper.map(spot, SpotDTO.class);
        if (spot.getPhoto() != null) {
            dto.setPhoto("data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(spot.getPhoto()));
        }
        return dto;
    }

    private Spot mapToEntity(SpotDTO spotDTO) {
        return modelMapper.map(spotDTO, Spot.class);
    }
}
