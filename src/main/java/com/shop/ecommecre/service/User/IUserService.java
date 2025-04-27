package com.shop.ecommecre.service.User;

import com.shop.ecommecre.dto.request.CreateUserRequest;
import com.shop.ecommecre.dto.request.UpdateUserRequest;
import com.shop.ecommecre.model.User;

public interface IUserService {
    User getUserById(Long uderId);
    User createUser(CreateUserRequest userRequest);
    User updateUser(Long userId, UpdateUserRequest userRequest);
    void deleteUser(Long userId);
}
