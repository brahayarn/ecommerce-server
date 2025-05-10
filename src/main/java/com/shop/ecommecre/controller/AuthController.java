package com.shop.ecommecre.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.ecommecre.data.RoleRepository;
import com.shop.ecommecre.dto.request.LoginRequest;
import com.shop.ecommecre.dto.request.SignUpRequest;
import com.shop.ecommecre.dto.response.api;
import com.shop.ecommecre.dto.response.JwtResponse;
import com.shop.ecommecre.model.Role;
import com.shop.ecommecre.model.User;
import java.util.Set;
import java.util.stream.Collectors;

import com.shop.ecommecre.repository.UserRepository;
import com.shop.ecommecre.security.jwt.Utils;
import com.shop.ecommecre.security.user.EcommerceUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final Utils jwtUtils;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
public ResponseEntity<api> login(@Valid @RequestBody LoginRequest request) {
    try {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateToken(authentication);
        EcommerceUserDetails userDetails = (EcommerceUserDetails) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities().stream()
                                      .map(GrantedAuthority::getAuthority)
                                      .collect(Collectors.toSet());

        JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt, roles);

        return ResponseEntity.ok(new api("Login Successful", jwtResponse));
    } catch (AuthenticationException e) {
        return ResponseEntity.status(401).body(new api(e.getMessage(), null));
    }
}


    @PostMapping("/signup")
    public ResponseEntity<api> signup(@Valid @RequestBody SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new api("Email is already in use", null));
        }

        String roleName = request.getRole() != null ? request.getRole() : "ROLE_USER";
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException(roleName + " not found"));

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return ResponseEntity.ok(new api("User registered successfully with role: " + roleName, null));
    }

}
