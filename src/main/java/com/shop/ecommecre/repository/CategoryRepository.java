package com.shop.ecommecre.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.ecommecre.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
