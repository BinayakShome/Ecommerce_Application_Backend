package com.binayak.ecomm_backend.controller;

import com.binayak.ecomm_backend.entity.Product;
import com.binayak.ecomm_backend.payload.ProductDto;
import com.binayak.ecomm_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/seller/{sellerId}/category/{categoryId}")
    public ResponseEntity<ProductDto> addProduct(
            @Valid @RequestBody ProductDto producttDto,
            @PathVariable("sellerId") Integer sellerId,
            @PathVariable("categoryId") Integer categoryId
    ) {
        ProductDto productDto = this.productService.createProduct(producttDto, categoryId, sellerId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}
