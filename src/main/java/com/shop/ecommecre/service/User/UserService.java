package com.shop.ecommecre.service.User;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shop.ecommecre.dto.request.CreateUserRequest;
import com.shop.ecommecre.dto.request.UpdateUserRequest;
import com.shop.ecommecre.exceptions.AlreadyExistsException;
import com.shop.ecommecre.exceptions.ResourceNotFoundException;
import com.shop.ecommecre.model.User;
import com.shop.ecommecre.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public User createUser(CreateUserRequest userRequest) {
        return  Optional.of(userRequest)
                .filter(user -> !userRepository.existsByEmail(userRequest.getEmail()))
                .map(request -> {
                    User user = new User();
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());
                    user.setFirstName(userRequest.getFirstName());
                    user.setLastName(userRequest.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +userRequest.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest userRequest) {
        return  userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(userRequest.getFirstName());
            existingUser.setLastName(userRequest.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found!");
        });
    }
}
