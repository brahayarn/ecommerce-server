package com.shop.ecommecre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.ecommecre.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long productId);
}
