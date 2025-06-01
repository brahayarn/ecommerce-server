package com.shop.ecommecre.dto.orderDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private String userEmail;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> items;
}
