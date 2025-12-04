package com.cart.client;

import com.cart.dtos.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gateway")
public interface ProductClient {

    @GetMapping("/api/product/id/{id}")
    ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id);

}
