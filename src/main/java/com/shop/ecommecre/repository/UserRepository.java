package com.shop.ecommecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.ecommecre.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
