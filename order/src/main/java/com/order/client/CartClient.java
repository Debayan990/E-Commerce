package com.order.client;

import com.order.dtos.CartResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gateway",contextId = "cart-client")
public interface CartClient {

    @GetMapping("/api/cart")
    ResponseEntity<CartResponseDTO> getAll();

    @DeleteMapping("/api/cart/delete")
    ResponseEntity<String> removeFromCart();
}
