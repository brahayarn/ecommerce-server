package com.shop.ecommecre.service.Image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.shop.ecommecre.dto.imageDto.ImageDto;
import com.shop.ecommecre.model.Image;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageid);
  
    
}
