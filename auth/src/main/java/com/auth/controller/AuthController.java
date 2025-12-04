package com.auth.controller;

import com.auth.dtos.*;
import com.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    ResponseEntity<UserOutputDTO> login(@RequestBody UserInputDTO dto){
        return ResponseEntity.status(201).body(authService.login(dto));
    }
    @PostMapping("/register")
    ResponseEntity<RegisterOutput> register(@RequestBody UserInputDTO dto){
        return ResponseEntity.status(201).body(authService.register(dto));
    }

    @PostMapping("/sellerregister")
    ResponseEntity<RegisterOutput> registerSeller(@RequestBody SellerRegister dto){
        return ResponseEntity.status(201).body(authService.sellerRegister(dto));
    }

    @PostMapping("/sellerLogin")
    ResponseEntity<SellerOutputDTO> loginSeller(@RequestBody SellerLoginDTO dto){
        return ResponseEntity.status(201).body(authService.sellerLogin(dto));
    }
}
