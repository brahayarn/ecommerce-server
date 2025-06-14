package com.shop.ecommecre.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Cart;
import com.shop.ecommecre.model.User;
import com.shop.ecommecre.service.Cart.ICartService;
import com.shop.ecommecre.service.Cart.CartItem.ICartItemService;
import com.shop.ecommecre.service.User.IUserService;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/cartItem")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("/add")
    public ResponseEntity<api> addItemToCart(@RequestParam Long productId,@RequestParam int quantity) {
        try{
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initialNewCart(user);
        cartItemService.addItemToCart(cart.getId(), productId, quantity);
        return ResponseEntity.ok(new api("Item added to cart", null));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error", e.getMessage()));
        } 
        catch (JwtException e) {
            return ResponseEntity.status(401).body(new api("Error", e.getMessage()));
        }
    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<api> removeItemfromCart(@PathVariable Long cartId,@PathVariable Long productId) {
        try{
        cartItemService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(new api("Item removed from cart", null));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(400).body(new api("Error", e.getMessage()));
        }
    }

    @PutMapping("/update/{cartId}/{productId}")
    public ResponseEntity<api> updateItemQuantity(@PathVariable Long cartId,@PathVariable Long itemId,@RequestParam int quantity) {
        try{
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        return ResponseEntity.ok(new api("Item quantity updated", null));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(400).body(new api("Error", e.getMessage()));
        }
    }
}
