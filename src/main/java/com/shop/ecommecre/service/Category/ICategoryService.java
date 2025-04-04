package com.shop.ecommecre.service.Category;

import java.util.List;

import com.shop.ecommecre.model.Category;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
