package com.shop.ecommecre.service.Cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Cart;
import com.shop.ecommecre.repository.CartItemRepository;
import com.shop.ecommecre.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCartById(Long id) {
        Cart cart = cartRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCartById(id);
        cartItemRepository.deleteAll(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);
        
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCartById(id);
        return cart.getTotalAmount();
    }
    
}
