package com.shop.ecommecre.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Order;
import com.shop.ecommecre.service.Order.IOrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<api> createOrder(@RequestParam Long userId) {
         try {
            Order order =  orderService.createOrder(userId);
            return ResponseEntity.ok(new api("Item Order Success!", order));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new api("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/get")
    public ResponseEntity<api> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new api("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<api> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> order = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(new api("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new api("Error Occured!", e.getMessage()));
        }
    }
}
