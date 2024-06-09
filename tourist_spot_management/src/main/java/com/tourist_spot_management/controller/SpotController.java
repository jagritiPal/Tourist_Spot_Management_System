package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.SpotDTO;
import com.tourist_spot_management.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/spots")
public class SpotController {

    @Autowired
    private SpotService spotService;

    // http://localhost:8080/api/spots/add
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<SpotDTO> createSpot(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("spotName") String spotName,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("history") String history,
            @RequestParam("openingHours") String openingHours,
            @RequestParam("entryFee") Double entryFee) throws IOException {

        SpotDTO spotDTO = new SpotDTO();
        spotDTO.setSpotName(spotName);
        spotDTO.setDescription(description);
        spotDTO.setLocation(location);
        spotDTO.setHistory(history);
        spotDTO.setOpeningHours(openingHours);
        spotDTO.setEntryFee(entryFee);

        SpotDTO createdSpot = spotService.createTourist(spotDTO, photo);
        return new ResponseEntity<>(createdSpot, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/spots
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<SpotDTO>> getAllSpots() {
        List<SpotDTO> spots = spotService.getAllTouristSpots();
        return new ResponseEntity<>(spots, HttpStatus.OK);
    }

    // http://localhost:8080/api/spots/1
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{spotId}")
    public ResponseEntity<SpotDTO> getSpotById(@PathVariable Long spotId) {
        SpotDTO spot = spotService.getTouristSpotById(spotId);
        return new ResponseEntity<>(spot, HttpStatus.OK);
    }

    // http://localhost:8080/api/spots/1
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{spotId}")
    public ResponseEntity<SpotDTO> updateSpot(
            @PathVariable Long spotId,
            @RequestParam(value = "photo", required = false) MultipartFile photo,
            @RequestParam("spotName") String spotName,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("history") String history,
            @RequestParam("openingHours") String openingHours,
            @RequestParam("entryFee") Double entryFee) throws IOException {

        SpotDTO spotDTO = new SpotDTO();
        spotDTO.setSpotName(spotName);
        spotDTO.setDescription(description);
        spotDTO.setLocation(location);
        spotDTO.setHistory(history);
        spotDTO.setOpeningHours(openingHours);
        spotDTO.setEntryFee(entryFee);

        SpotDTO updatedSpot = spotService.updateTouristSpot(spotId, spotDTO, photo);
        return new ResponseEntity<>(updatedSpot, HttpStatus.OK);
    }

    // http://localhost:8080/api/spots/1
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{spotId}")
    public ResponseEntity<String> deleteSpot(@PathVariable Long spotId) {
        spotService.deleteTouristSpot(spotId);
        return new ResponseEntity<>("Spot is deleted successfully.", HttpStatus.OK);
    }

    // http://localhost:8080/api/spots/search/location/Paris, France
    @GetMapping("/search/location/{location}")
    public ResponseEntity<List<SpotDTO>> searchByLocation(@PathVariable String location) {
        List<SpotDTO> spots = spotService.searchByLocation(location);
        return new ResponseEntity<>(spots, HttpStatus.OK);
    }

    // http://localhost:8080/api/spots/search/name/Eiffel Tower
    @GetMapping("/search/name/{spotName}")
    public ResponseEntity<List<SpotDTO>> searchBySpotName(@PathVariable String spotName) {
        List<SpotDTO> spots = spotService.searchBySpotName(spotName);
        return new ResponseEntity<>(spots, HttpStatus.OK);
    }

    // http://localhost:8080/api/spots/1/reviews
    @GetMapping("/{spotId}/reviews")
    public ResponseEntity<SpotDTO> getAllReviewsForParticularSpot(@PathVariable Long spotId) {
        SpotDTO spotDTO = spotService.getAllReviewsForParticularSpot(spotId);
        return new ResponseEntity<>(spotDTO, HttpStatus.OK);
    }

}
