package com.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    ResponseEntity<Object> handleUserAlready(UserAlreadyRegisteredException ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        response.put("status",409);
        return ResponseEntity.status(409).body(response);
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleUserAlready(Exception ex){
        Map<String,Object> response=new HashMap<>();
        response.put("error",ex.getMessage());
        return ResponseEntity.status(500).body(response);
    }

}
