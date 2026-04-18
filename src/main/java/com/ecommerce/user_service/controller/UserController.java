package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.dto.ApiResponse;
import com.ecommerce.user_service.dto.UserRequestDTO;
import com.ecommerce.user_service.dto.UserResponseDTO;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> registerUser(@RequestBody UserRequestDTO request) {

        UserResponseDTO response = userService.registerUser(request);
        return new ApiResponse<>("success", "User registered successfully",response);
    }
}
