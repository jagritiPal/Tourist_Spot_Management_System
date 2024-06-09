package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Guide;
import com.tourist_spot_management.entity.Role;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.GuideDTO;
import com.tourist_spot_management.repository.GuideRepository;
import com.tourist_spot_management.repository.RoleRepository;
import com.tourist_spot_management.service.GuideService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl implements GuideService {

    @Autowired
    private GuideRepository guideRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public GuideDTO createGuide(GuideDTO guideDTO, MultipartFile profilePhoto) throws IOException {
        Guide guide = mapToEntity(guideDTO);
        // Encode password before saving
        guide.setPassword(passwordEncoder.encode(guide.getPassword()));
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            guide.setProfilePhoto(profilePhoto.getBytes());
        }

        // Retrieve the role from the repository
        Role guideRole = roleRepository.findByRoleName("ROLE_GUIDE")
                .orElseThrow(() -> new RuntimeException("ROLE_GUIDE not found in the database"));

        // Set the role using Collections.singleton
        guide.setRoles(Collections.singleton(guideRole));

        Guide savedGuide = guideRepository.save(guide);
        return mapToDto(savedGuide);
    }

    @Override
    public List<GuideDTO> getAllGuides() {
        List<Guide> guides = guideRepository.findAll();
        return guides.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuideDTO getGuideById(Long guideId) {
        Guide guide = guideRepository.findById(guideId)
                .orElseThrow(() -> new ResourceNotFoundException("Guide not found with id: " + guideId));
        return mapToDto(guide);
    }

    @Override
    public GuideDTO updateGuide(Long guideId, GuideDTO guideDTO, MultipartFile profilePhoto) throws IOException {
        Guide existingGuide = guideRepository.findById(guideId)
                .orElseThrow(() -> new ResourceNotFoundException("Guide not found with id: " + guideId));

        existingGuide.setUsername(guideDTO.getUsername());
        existingGuide.setPassword(passwordEncoder.encode(guideDTO.getPassword())); // Encode new password
        existingGuide.setFirstName(guideDTO.getFirstName());
        existingGuide.setLastName(guideDTO.getLastName());
        existingGuide.setMobile(guideDTO.getMobile());
        existingGuide.setEmail(guideDTO.getEmail());
        existingGuide.setBio(guideDTO.getBio());
        existingGuide.setLanguage(guideDTO.getLanguage());
        existingGuide.setAvailableDates(guideDTO.getAvailableDates());
        existingGuide.setFees(guideDTO.getFees());
        existingGuide.setLocation(guideDTO.getLocation());
        existingGuide.setSpotName(guideDTO.getSpotName());

        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            existingGuide.setProfilePhoto(profilePhoto.getBytes());
        }

        Guide updatedGuide = guideRepository.save(existingGuide);
        return mapToDto(updatedGuide);
    }

    @Override
    public void deleteGuide(Long guideId) {
        if (!guideRepository.existsById(guideId)) {
            throw new ResourceNotFoundException("Guide not found with id: " + guideId);
        }
        guideRepository.deleteById(guideId);
    }

    private GuideDTO mapToDto(Guide guide) {
        GuideDTO dto = modelMapper.map(guide, GuideDTO.class);
        // Assuming your profile photo is Base64 encoded string, if not, adjust accordingly
        if (guide.getProfilePhoto() != null) {
            dto.setProfilePhoto("data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(guide.getProfilePhoto()));
        }
        return dto;
    }

    private Guide mapToEntity(GuideDTO guideDTO) {
        Guide guide = modelMapper.map(guideDTO, Guide.class);
        // You may need to handle profile photo differently if it's not a Base64 encoded string
        return guide;
    }
}
