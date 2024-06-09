package com.tourist_spot_management.controller;

import com.tourist_spot_management.payload.PhotoDTO;
import com.tourist_spot_management.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    //http://localhost:8080/api/photos/add
    @PostMapping("/add")
    public ResponseEntity<PhotoDTO> createPhoto(@RequestBody PhotoDTO photoDTO) {
        PhotoDTO createdPhoto = photoService.createPhoto(photoDTO);
        return new ResponseEntity<>(createdPhoto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/photos/1
    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoDTO> getPhotoById(@PathVariable Long photoId) {
        PhotoDTO photoDTO = photoService.getPhotoById(photoId);
        return new ResponseEntity<>(photoDTO, HttpStatus.OK);
    }

    //http://localhost:8080/api/photos/spot/1
    @GetMapping("/spot/{spotId}")
    public ResponseEntity<List<PhotoDTO>> getPhotosBySpotId(@PathVariable Long spotId) {
        List<PhotoDTO> photos = photoService.getPhotosBySpotId(spotId);
        return new ResponseEntity<>(photos, HttpStatus.OK);
    }

    //http://localhost:8080/api/photos/1
    @PutMapping("/{photoId}")
    public ResponseEntity<PhotoDTO> updatePhoto(@PathVariable Long photoId, @RequestBody PhotoDTO photoDTO) {
        PhotoDTO updatedPhoto = photoService.updatePhoto(photoId, photoDTO);
        return new ResponseEntity<>(updatedPhoto, HttpStatus.OK);
    }

    //http://localhost:8080/api/photos/1
    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId) {
        photoService.deletePhoto(photoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
