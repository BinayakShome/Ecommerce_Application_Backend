package com.binayak.ecomm_backend.repo;

import com.binayak.ecomm_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findByCategoryTitle(String categoryTitle);
}
