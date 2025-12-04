package com.order.controller;

import com.order.dtos.AuthPrincipal;
import com.order.dtos.OrderInputDTO;
import com.order.dtos.OrderOutputDTO;
import com.order.security.JwtUtil;
import com.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/get")
    ResponseEntity<List<OrderOutputDTO>> getOrder(Authentication authentication){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long userId=authPrincipal.userid();
        return ResponseEntity.status(200).body(orderService.getOrderOutput(userId));
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    ResponseEntity<String> addOrder(Authentication authentication,@RequestBody OrderInputDTO dto){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long userId=authPrincipal.userid();
        return ResponseEntity.status(201).body(orderService.addOrder(userId,dto));
    }


}
