package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.GuideDTO;
import com.tourist_spot_management.service.GuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/guides")
public class GuideController {

    @Autowired
    private GuideService guideService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GUIDE')")
    @PostMapping("/add")
    public ResponseEntity<GuideDTO> createGuide(
            @RequestParam("profilePhoto") MultipartFile profilePhoto,
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("firstName") String firstName,
            @Valid @RequestParam("lastName") String lastName,
            @Valid @RequestParam("mobile") String mobile,
            @Valid @RequestParam("email") String email,
            @RequestParam("bio") String bio,
            @RequestParam("language") String language,
            @RequestParam("availableDates") String availableDates,
            @RequestParam("fees") Double fees,
            @RequestParam("location") String location,
            @RequestParam("spotName") String spotName) throws IOException {

        GuideDTO guideDTO = new GuideDTO();
        guideDTO.setUsername(username);
        guideDTO.setPassword(password);
        guideDTO.setFirstName(firstName);
        guideDTO.setLastName(lastName);
        guideDTO.setMobile(mobile);
        guideDTO.setEmail(email);
        guideDTO.setBio(bio);
        guideDTO.setLanguage(language);
        guideDTO.setAvailableDates(availableDates);
        guideDTO.setFees(fees);
        guideDTO.setLocation(location);
        guideDTO.setSpotName(spotName);

        GuideDTO createdGuide = guideService.createGuide(guideDTO, profilePhoto);
        return new ResponseEntity<>(createdGuide, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GUIDE')")
    @GetMapping
    public ResponseEntity<List<GuideDTO>> getAllGuides() {
        List<GuideDTO> guides = guideService.getAllGuides();
        return new ResponseEntity<>(guides, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GUIDE')")
    @GetMapping("/{guideId}")
    public ResponseEntity<GuideDTO> getGuideById(@PathVariable Long guideId) {
        GuideDTO guide = guideService.getGuideById(guideId);
        return new ResponseEntity<>(guide, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GUIDE')")
    @PutMapping("/{guideId}")
    public ResponseEntity<GuideDTO> updateGuide(
            @PathVariable Long guideId,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto,
            @Valid @RequestParam("username") String username,
            @Valid @RequestParam("password") String password,
            @Valid @RequestParam("firstName") String firstName,
            @Valid @RequestParam("lastName") String lastName,
            @Valid @RequestParam("mobile") String mobile,
            @Valid @RequestParam("email") String email,
            @RequestParam("bio") String bio,
            @RequestParam("language") String language,
            @RequestParam("availableDates") String availableDates,
            @RequestParam("fees") Double fees,
            @RequestParam("location") String location,
            @RequestParam("spotName") String spotName) throws IOException {

        GuideDTO guideDTO = new GuideDTO();
        guideDTO.setUsername(username);
        guideDTO.setPassword(password);
        guideDTO.setFirstName(firstName);
        guideDTO.setLastName(lastName);
        guideDTO.setMobile(mobile);
        guideDTO.setEmail(email);
        guideDTO.setBio(bio);
        guideDTO.setLanguage(language);
        guideDTO.setAvailableDates(availableDates);
        guideDTO.setFees(fees);
        guideDTO.setLocation(location);
        guideDTO.setSpotName(spotName);

        GuideDTO updatedGuide = guideService.updateGuide(guideId, guideDTO, profilePhoto);
        return new ResponseEntity<>(updatedGuide, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_GUIDE')")
    @DeleteMapping("/{guideId}")
    public ResponseEntity<String> deleteGuide(@PathVariable Long guideId) {
        guideService.deleteGuide(guideId);
        return new ResponseEntity<>("Guide is deleted successfully.", HttpStatus.OK);
    }
}
