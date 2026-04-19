package com.ecommerce.user_service.service;

import com.ecommerce.user_service.dto.UserRequestDTO;
import com.ecommerce.user_service.dto.UserResponseDTO;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.exception.UserAlreadyExistsException;
import com.ecommerce.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO registerUser(UserRequestDTO request) {

     logger.info("Registering user with email:{}",request.getEmail());
        // 1. Check duplicate
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            logger.error("User already exists with email: {}", request.getEmail());
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
        logger.info("saving user to database");

        // 3. Save
        User savedUser = userRepository.save(user);
        logger.info("User saved with id:{}",savedUser.getId());

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
