package com.shop.ecommecre.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.service.Category.ICategoryService;

import lombok.RequiredArgsConstructor;
import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.exceptions.AlreadyExistsException;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Category;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<api> getAllCategories() {
        try{
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(new api("Found categories", categories));
        }catch (Exception e){
            return ResponseEntity.status(500).body(new api("Error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<api> addCategory (@RequestBody Category name) {
        try {
        Category category = categoryService.addCategory(name);
        return ResponseEntity.ok(new api("Category added", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(500).body(new api("Error", e.getMessage()));
        }
    }

    @GetMapping("/category/{id}/getid")
    public ResponseEntity<api> getCategoryById (@PathVariable Long id) {
        try {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new api("Category found by id", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error", e.getMessage()));
        }
    }

    @GetMapping("/category/{name}/getname")
    public ResponseEntity<api> getCategoryByName(@PathVariable String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new api("Category found by name", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<api> deleteCategory(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            return  ResponseEntity.ok(new api("Category deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<api> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try {
            Category updatedCategory = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(new api("Category updated", updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
    }
}
