package com.shop.ecommecre.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.ecommecre.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
