package com.shop.ecommecre.service.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shop.ecommecre.enums.OrderStatus;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Cart;
import com.shop.ecommecre.model.Order;
import com.shop.ecommecre.model.OrderItem;
import com.shop.ecommecre.model.Product;
import com.shop.ecommecre.repository.OrderRepository;
import com.shop.ecommecre.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Implement the methods from IOrderService interface
    @Override
    public Order createOrder(Long userId) {
        // Implementation here
        return null;
    }

    private List<OrderItem> createOrderItem(Order order, Cart cart) {
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    cartItem.getQuantity(),
                    cartItem.getPricePerItem(),
                    order,
                    product);
        }).collect(java.util.stream.Collectors.toList());

    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        //TO DO set the user
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        // Implementation here
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

}
