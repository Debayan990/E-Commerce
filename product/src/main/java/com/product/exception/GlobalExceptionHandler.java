package com.product.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserIdNotFoundException.class)
    ResponseEntity<Object> handleUserIdNotFound(UserIdNotFoundException ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    ResponseEntity<Object> handleCategoryNotFound(CategoryNotFoundException ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleGenricException(Exception ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }



}
