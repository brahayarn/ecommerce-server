package com.shop.ecommecre.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.dto.orderDto.OrderDto;
import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.enums.OrderStatus;
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
            Order order = orderService.createOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new api("Item Order Success!", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new api("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/get")
    public ResponseEntity<api> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new api("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api("Error Occured!", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<api> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(new api("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new api("Error Occured!", e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<api> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        try {
            if (status != OrderStatus.CANCELED && !isAdmin()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new api("Error: Users can only cancel orders.", "Permission denied."));
            }

            OrderDto updatedOrder = orderService.updateOrderStatus(orderId, status);
            return ResponseEntity.ok(new api("Order status updated successfully!", updatedOrder));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new api("Error occurred!", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new api("Error occurred!", e.getMessage()));
        }
    }
    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<api> getAllOrders() {
        try {
            List<OrderDto> orders = orderService.getAllOrders();
            return ResponseEntity.ok(new api("All Orders Retrieved Successfully!", orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new api("Error occurred!", e.getMessage()));
        }
    }

}
