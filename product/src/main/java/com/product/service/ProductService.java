package com.product.service;

import com.product.dtos.ProductRequestDTO;
import com.product.dtos.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAll();
    ProductResponseDTO getByProductId(Long id);
    List<ProductResponseDTO> getById(Long id);
    List<ProductResponseDTO> getByCategories(String categories);
    ProductResponseDTO addProduct(Long sellerId,ProductRequestDTO dto);
    ProductResponseDTO updateProduct(Long sellerId,ProductRequestDTO dto,Long id);
    void deleteById(Long sellerId,Long id);
}
