package com.cart.controller;

import com.cart.dtos.AuthPrincipal;
import com.cart.dtos.CartInputDTO;
import com.cart.dtos.CartResponseDTO;
import com.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    ResponseEntity<CartResponseDTO> getAll(Authentication authentication){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long userId= authPrincipal.userid();
        return ResponseEntity.status(200).body(cartService.getAll(userId));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    ResponseEntity<String> addToCart(Authentication authentication, @RequestBody CartInputDTO dto){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long userId=authPrincipal.userid();
        return ResponseEntity.status(201).body(cartService.addToCart(userId,dto));
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete")
    ResponseEntity<String> removeFromCart(Authentication authentication){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long userId= authPrincipal.userid();
        return ResponseEntity.status(202).body(cartService.removeFromCart(userId));
    }

}
