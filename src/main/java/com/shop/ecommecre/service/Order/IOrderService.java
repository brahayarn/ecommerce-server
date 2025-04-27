package com.shop.ecommecre.service.Order;

import com.shop.ecommecre.model.Order;

public interface IOrderService {
    Order createOrder(Long userId);
    Order getOrder(Long orderId);
}
