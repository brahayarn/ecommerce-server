package com.shop.ecommecre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.dto.request.CreateUserRequest;
import com.shop.ecommecre.dto.request.UpdateUserRequest;
import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.dto.userDto.UserDto;
import com.shop.ecommecre.exceptions.AlreadyExistsException;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.User;
import com.shop.ecommecre.service.User.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/users")
public class UserController {
    private final IUserService userService;
    
    @GetMapping("/{userId}/get")
    public ResponseEntity<api> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new api("Success", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<api> createUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new api("Create User Success!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(409).body(new api(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<api> updateUser(@RequestBody UpdateUserRequest request, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(userId, request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new api("Update User Success!", userDto));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<api> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new api("Delete User Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new api(e.getMessage(), null));
        }
    }
}
