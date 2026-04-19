package com.ecommerce.user_service.exception;

import com.ecommerce.user_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>>handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ApiResponse<Object> response = new ApiResponse<>("ERROR", ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>handleGenericException(Exception ex){
        ApiResponse<Object>response = new ApiResponse<>("ERROR","Something went wrong",null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
