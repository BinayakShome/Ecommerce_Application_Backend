package com.binayak.ecomm_backend.service;

import com.binayak.ecomm_backend.entity.Category;
import com.binayak.ecomm_backend.entity.Product;
import com.binayak.ecomm_backend.payload.CategoryDto;
import com.binayak.ecomm_backend.payload.ProductDto;
import com.binayak.ecomm_backend.payload.UserDto;
import com.binayak.ecomm_backend.response.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    ProductDto createProduct(ProductDto productDto, Integer categoryId, Integer sellerId);

    ProductDto updateProduct(ProductDto productDto, Integer productId);

    void deleteProduct(Integer productId);

    ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ProductDto getById(Integer productId);

    ProductResponse getByCategory(Integer category_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ProductResponse searchProduct(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
