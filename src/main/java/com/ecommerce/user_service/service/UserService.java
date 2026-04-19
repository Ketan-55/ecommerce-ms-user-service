package com.ecommerce.user_service.service;

import com.ecommerce.user_service.dto.UserRequestDTO;
import com.ecommerce.user_service.dto.UserResponseDTO;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.exception.UserAlreadyExistsException;
import com.ecommerce.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO registerUser(UserRequestDTO request) {

        // 1. Check duplicate
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        // 2. Map DTO → Entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        // Role assigned by system (NOT client)
        user.setRole("USER");

        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("SYSTEM");

        // 3. Save
        User savedUser = userRepository.save(user);

        // 4. Map Entity → Response DTO
        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setCreatedBy(savedUser.getCreatedBy());

        return response;
    }

}
