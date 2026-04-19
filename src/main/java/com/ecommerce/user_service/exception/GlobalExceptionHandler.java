package com.ecommerce.user_service.exception;

import com.ecommerce.user_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>handleVaidation(MethodArgumentNotValidException ex){
        List<String>errors = new ArrayList<>();
        List<FieldError> errorMessage = ex.getBindingResult().getFieldErrors();
        for(FieldError fieldError:errorMessage){
            errors.add(fieldError.getDefaultMessage());
        }
        ApiResponse<Object>response = new ApiResponse<>("ERROR","Validation failed",errors);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


}
