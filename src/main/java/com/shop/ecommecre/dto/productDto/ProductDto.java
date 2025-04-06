package com.shop.ecommecre.dto.productDto;

import java.math.BigDecimal;
import java.util.List;

import com.shop.ecommecre.dto.imageDto.ImageDto;
import com.shop.ecommecre.model.Category;

import lombok.Data;


@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private List<ImageDto> images;
}
