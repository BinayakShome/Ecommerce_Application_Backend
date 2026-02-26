package com.binayak.ecomm_backend.payload;

import com.binayak.ecomm_backend.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

public class CategoryDto {
    private Integer categoryId;

    private String categoryTitle;

    private String categoryDescription;

    private List<Product> products = new ArrayList<>();
}
