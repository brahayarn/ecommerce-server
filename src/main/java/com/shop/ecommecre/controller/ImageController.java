package com.shop.ecommecre.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shop.ecommecre.dto.imageDto.ImageDto;
import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Image;
import org.springframework.core.io.Resource;
import com.shop.ecommecre.service.Image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<api> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new api("Images uploaded successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to upload images", null));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<api> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new api("Image updated successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
        return ResponseEntity.status(500).body(new api("Failed to update image", null));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<api> deleteImage(@PathVariable Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImage(imageId);
                return ResponseEntity.ok(new api("Delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
        return ResponseEntity.status(500).body(new api("Delete failed!", null));
    }

}