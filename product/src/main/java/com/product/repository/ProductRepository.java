package com.product.repository;

import com.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findByName(String name);

    Optional<List<Product>> findByCategory(String categories);

    Optional<List<Product>> findBySellerId(Long sellerId);
}
