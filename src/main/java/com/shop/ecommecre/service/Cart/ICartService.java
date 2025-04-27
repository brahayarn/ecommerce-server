package com.shop.ecommecre.service.Cart;

import java.math.BigDecimal;

import com.shop.ecommecre.model.Cart;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCart(Long cart);
    BigDecimal getTotalPrice(Long cartId);
    Long initialNewCart();
    Cart getCartByUserId(Long userId);
}
