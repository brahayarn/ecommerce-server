package com.shop.ecommecre.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Cart;
import com.shop.ecommecre.service.Cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/cart")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/{id}/getid")
    public ResponseEntity<api> getCartById(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCartById(id);
            return ResponseEntity.ok(new api("Cart found by id", cart));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<api> clearCart(@PathVariable Long id){
        cartService.clearCart(id);
        return ResponseEntity.ok(new api("Clear cart now", null));
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<api> getTotalPrice(@PathVariable Long id) {
        try {
            BigDecimal total = cartService.getTotalPrice(id);
            return ResponseEntity.ok(new api("Total amount", total));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error", e.getMessage()));
        }
    }
    // need write an exeption because in CartService method getCartById have
    // exception message
}
