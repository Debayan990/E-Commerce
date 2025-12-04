package com.product.controller;

import com.product.dtos.AuthPrincipal;
import com.product.dtos.ProductRequestDTO;
import com.product.dtos.ProductResponseDTO;
import com.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    ResponseEntity<List<ProductResponseDTO>> getAll(){
        return ResponseEntity.status(200).body(productService.getAll());
    }

    @GetMapping("/category/{category}")
    ResponseEntity<List<ProductResponseDTO>> getByCategories(@PathVariable String category){
        return ResponseEntity.status(200).body(productService.getByCategories(category));
    }

    @GetMapping("/id/{id}")
    ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.status(200).body(productService.getByProductId(id));
    }


    @PreAuthorize("hasRole('SELLER')")
    @GetMapping("/seller")
    ResponseEntity<List<ProductResponseDTO>> getById(Authentication authentication){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long sellerId=authPrincipal.userid();
        return ResponseEntity.status(200).body(productService.getById(sellerId));
    }
    @PreAuthorize("hasRole('SELLER')")
    @PostMapping("/add")
    ResponseEntity<ProductResponseDTO> addProduct(Authentication authentication, @RequestBody ProductRequestDTO dto){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long sellerId= authPrincipal.userid();

        return ResponseEntity.status(201).body(productService.addProduct(sellerId,dto));
    }
    @PreAuthorize("hasRole('SELLER')")
    @PutMapping("/update/{id}")
    ResponseEntity<ProductResponseDTO> updateProduct(Authentication authentication,@RequestBody ProductRequestDTO dto,@PathVariable Long id){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long sellerId= authPrincipal.userid();

        return ResponseEntity.status(202).body(productService.updateProduct(sellerId,dto,id));
    }
    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteProduct(Authentication authentication,@PathVariable Long id){
        AuthPrincipal authPrincipal= (AuthPrincipal) authentication.getPrincipal();
        Long sellerId= authPrincipal.userid();

        productService.deleteById(sellerId,id);
        return ResponseEntity.status(200).body("Product of id "+id+" deleted successfully");
    }
}
