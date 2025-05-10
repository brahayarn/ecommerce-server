package com.shop.ecommecre.service.Cart;

import java.math.BigDecimal;

import com.shop.ecommecre.model.Cart;
import com.shop.ecommecre.model.User;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCart(Long cart);
    BigDecimal getTotalPrice(Long cartId);
    Cart initialNewCart(User user);
    Cart getCartByUserId(Long userId);  
}
