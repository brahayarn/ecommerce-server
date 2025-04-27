package com.shop.ecommecre.dto.userDto;

import java.util.List;

import com.shop.ecommecre.dto.cartDto.CartDto;
import com.shop.ecommecre.dto.orderDto.OrderDto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
