package com.cart.exception;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartNotFoundException.class)
    ResponseEntity<Object> handleCardNotFound(CartNotFoundException ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        response.put("status",404);
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    ResponseEntity<Object> handleFeignNotFoundException(FeignException ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        response.put("status",404);
        return ResponseEntity.status(404).body(response);
    }


    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleException(Exception ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        response.put("status",500);
        return ResponseEntity.status(500).body(response);
    }

}
