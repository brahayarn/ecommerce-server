package com.shop.ecommecre.dto.cartDto;

import java.math.BigDecimal;

import com.shop.ecommecre.dto.productDto.ProductDto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
