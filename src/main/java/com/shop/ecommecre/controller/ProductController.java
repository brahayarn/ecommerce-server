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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.shop.ecommecre.dto.productDto.ProductDto;
import com.shop.ecommecre.dto.request.AddProductRequest;
import com.shop.ecommecre.dto.request.ProductUpdateRequest;
import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Product;
import com.shop.ecommecre.service.Product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<api> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new api("Products retrieved successfully", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve products", null));
        }
    }

    @GetMapping("/products/{productId}/product")
    public ResponseEntity<api> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new api("Product retrieved successfully", productDto));
        } catch (ResourceAccessException e) {
            return ResponseEntity.status(404).body(new api("Failed to retrieve product", null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<api> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product newProduct = productService.addProduct(product);
            return ResponseEntity.status(201).body(new api("Product added successfully", newProduct));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to add product", null));
        }
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<api> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long productId) {
        try {
            Product updatedProduct = productService.updateProduct(product, productId);
            ProductDto productDto = productService.convertToDto(updatedProduct);
            return ResponseEntity.ok(new api("Product updated successfully", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(500).body(new api("Failed to update product", null));
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<api> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new api("Product deleted successfully", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(500).body(new api("Failed to delete product", null));
        }
    }

    @GetMapping("/products/{brand}/brand/{name}/name")
    public ResponseEntity<api> getProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new api("No products found", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new api("Products retrieved successfully", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve products", null));
        }
    }

    @GetMapping("/products/{category}/category/{brand}/brand")
    public ResponseEntity<api> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new api("No products found", null));
            }
            return ResponseEntity.ok(new api("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve products", null));
        }
    }

    @GetMapping("/products/{name}/productname")
    public ResponseEntity<api> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new api("No products found", null));
            }
            return ResponseEntity.ok(new api("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve products", null));
        }
    }

    @GetMapping("/bybrand")
    public ResponseEntity<api> findProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new api("No products found", null));
            }
            return ResponseEntity.ok(new api("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve products", null));
        }
    }

    @GetMapping("/{category}/all")
    public ResponseEntity<api> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(404).body(new api("No products found", null));
            }
            return ResponseEntity.ok(new api("Products retrieved successfully", products));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve products", null));
        }
    }

    @GetMapping("/products/{brand}/count/{name}")
    public ResponseEntity<api> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            Long count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new api("Product count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Failed to retrieve product count", null));
        }
    }

}
