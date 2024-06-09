package com.tourist_spot_management.service;

import com.tourist_spot_management.payload.PhotoDTO;

import java.util.List;

public interface PhotoService {
    PhotoDTO createPhoto(PhotoDTO photoDTO);
    PhotoDTO getPhotoById(Long photoId);
    List<PhotoDTO> getPhotosBySpotId(Long spotId);
    PhotoDTO updatePhoto(Long photoId, PhotoDTO photoDTO);
    void deletePhoto(Long photoId);
}
