package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.GuideDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GuideService {

    GuideDTO createGuide(GuideDTO guideDTO, MultipartFile profilePhoto) throws IOException;

    List<GuideDTO> getAllGuides();

    GuideDTO getGuideById(Long guideId);

    GuideDTO updateGuide(Long guideId, GuideDTO guideDTO, MultipartFile profilePhoto) throws IOException;

    void deleteGuide(Long guideId);
}
