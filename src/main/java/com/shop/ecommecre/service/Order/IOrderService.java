package com.shop.ecommecre.service.Order;

import java.util.List;

import com.shop.ecommecre.model.Order;

public interface IOrderService {
    Order createOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getOrdersByUserId(Long userId);
}
