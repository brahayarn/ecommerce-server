package com.shop.ecommecre.service.Order;

import java.util.List;

import com.shop.ecommecre.dto.orderDto.OrderDto;
import com.shop.ecommecre.model.Order;

public interface IOrderService {
    Order createOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getOrdersByUserId(Long userId);
    OrderDto convertToDto(Order order);
}
