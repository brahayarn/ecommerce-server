package com.shop.ecommecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.ecommecre.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
