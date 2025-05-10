package com.shop.ecommecre.service.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shop.ecommecre.dto.orderDto.OrderDto;
import com.shop.ecommecre.enums.OrderStatus;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.Cart;
import com.shop.ecommecre.model.Order;
import com.shop.ecommecre.model.OrderItem;
import com.shop.ecommecre.model.Product;
import com.shop.ecommecre.repository.OrderRepository;
import com.shop.ecommecre.repository.ProductRepository;
import com.shop.ecommecre.service.Cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    // Implement the methods from IOrderService interface
    @Override
    public Order createOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItem(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId());
        return savedOrder;
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
        order.setUser(cart.getUser());
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
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToDto)
                .toList();
    }
    
    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    };

    @Override
    public OrderDto updateOrderStatus(Long orderId, OrderStatus status) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        order.setOrderStatus(status);
        orderRepository.save(order);

        return modelMapper.map(order, OrderDto.class); 
    }
}
