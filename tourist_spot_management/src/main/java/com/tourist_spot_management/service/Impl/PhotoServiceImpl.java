package com.tourist_spot_management.service.Impl;

import com.tourist_spot_management.entity.Photo;
import com.tourist_spot_management.exception.ResourceNotFoundException;
import com.tourist_spot_management.payload.PhotoDTO;
import com.tourist_spot_management.repository.PhotoRepository;
import com.tourist_spot_management.service.PhotoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PhotoDTO createPhoto(PhotoDTO photoDTO) {
        Photo photo = mapToEntity(photoDTO);
        Photo savedPhoto = photoRepository.save(photo);
        return mapToDto(savedPhoto);
    }

    @Override
    public PhotoDTO getPhotoById(Long photoId) {
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found with id: " + photoId));
        return mapToDto(photo);
    }

    @Override
    public List<PhotoDTO> getPhotosBySpotId(Long spotId) {
        List<Photo> photos = photoRepository.findBySpotId(spotId);
        return photos.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PhotoDTO updatePhoto(Long photoId, PhotoDTO photoDTO) {
        Photo existingPhoto = photoRepository.findById(photoId)
                .orElseThrow(() -> new ResourceNotFoundException("Photo not found with id: " + photoId));

        existingPhoto.setSpotId(photoDTO.getSpotId());
        existingPhoto.setUrl(photoDTO.getUrl());
        existingPhoto.setDescription(photoDTO.getDescription());

        Photo updatedPhoto = photoRepository.save(existingPhoto);
        return mapToDto(updatedPhoto);
    }

    @Override
    public void deletePhoto(Long photoId) {
        if (!photoRepository.existsById(photoId)) {
            throw new ResourceNotFoundException("Photo not found with id: " + photoId);
        }
        photoRepository.deleteById(photoId);
    }

    private PhotoDTO mapToDto(Photo photo) {
        return modelMapper.map(photo, PhotoDTO.class);
    }

    private Photo mapToEntity(PhotoDTO photoDTO) {
        return modelMapper.map(photoDTO, Photo.class);
    }
}
