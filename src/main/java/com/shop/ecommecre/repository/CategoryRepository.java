package com.shop.ecommecre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop.ecommecre.model.Category;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
