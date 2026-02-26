package com.binayak.ecomm_backend.repo;

import com.binayak.ecomm_backend.entity.Category;
import com.binayak.ecomm_backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    // Search products by name or description
    Page<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
            String nameKeyword, String descKeyword, Pageable pageable);

    // Optional: Find products in a specific price range
    Page<Product> findByProductPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCase(String product, Pageable pageable);
}
