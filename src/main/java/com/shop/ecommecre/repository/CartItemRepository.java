package com.shop.ecommecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.ecommecre.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByCartId(Long id);
}
    
