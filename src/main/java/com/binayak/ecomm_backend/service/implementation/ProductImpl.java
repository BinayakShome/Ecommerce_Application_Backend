package com.binayak.ecomm_backend.service.implementation;

import com.binayak.ecomm_backend.entity.Category;
import com.binayak.ecomm_backend.entity.Product;
import com.binayak.ecomm_backend.entity.User;
import com.binayak.ecomm_backend.exception.ResourceNotFoundException;
import com.binayak.ecomm_backend.payload.ProductDto;
import com.binayak.ecomm_backend.repo.CategoryRepo;
import com.binayak.ecomm_backend.repo.ProductRepo;
import com.binayak.ecomm_backend.repo.UserRepo;
import com.binayak.ecomm_backend.response.ProductResponse;
import com.binayak.ecomm_backend.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImpl implements ProductService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public ProductDto createProduct(ProductDto productDto, Integer categoryId, Integer sellerId) {

        User seller = this.userRepo.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("seller", "id", sellerId));

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

        Product product = this.modelMapper.map(productDto, Product.class);
        product.setSeller(seller);
        product.setCategory(category);

        Product newProduct = this.productRepo.save(product);

        return this.modelMapper.map(newProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID: ", productId));

        product.setProductPrice(productDto.getProductPrice());

        if (productDto.getCategory() != null && productDto.getCategory().getCategoryId() != null) {

            Integer newCategoryId = productDto.getCategory().getCategoryId();

            Category newCategory = categoryRepo.findById(newCategoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found with ID: " + newCategoryId));

            product.setCategory(newCategory);
        }
        Product updatedProduct = productRepo.save(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("product", "id", productId));

        productRepo.delete(product);
    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable page = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> pageProducts = this.productRepo.findAll(page);
        List<Product> allProducts = pageProducts.getContent();

        List<ProductDto> posts = allProducts
                .stream()
                .map((products -> this.modelMapper.map(products, ProductDto.class)))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProduct(posts);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setLastPage(pageProducts.isLast());
        productResponse.setTotalElements(pageProducts.getNumberOfElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());

        return productResponse;
    }

    @Override
    public ProductDto getById(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "id", productId));

        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductResponse getByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "id", categoryId));

        Page<Product> allProducts = this.productRepo.findByCategory(category, pageable);
        List<ProductDto> productsByCategory = allProducts.stream().map((products -> this.modelMapper.map(products, ProductDto.class))).collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setProduct(productsByCategory);

        productResponse.setPageNumber(allProducts.getNumber());
        productResponse.setPageSize(allProducts.getSize());
        productResponse.setTotalElements(allProducts.getTotalElements());
        productResponse.setTotalPages(allProducts.getTotalPages());
        productResponse.setLastPage(allProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchProduct(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Product> filterproducts = this.productRepo.findByProductNameContainingIgnoreCase(keyword, pageable);
        List<ProductDto> filtered = filterproducts.stream()
                .map(products -> this.modelMapper.map(products, ProductDto.class))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setProduct(filtered);

        productResponse.setPageNumber(filterproducts.getNumber());
        productResponse.setPageSize(filterproducts.getSize());
        productResponse.setTotalElements(filterproducts.getTotalElements());
        productResponse.setTotalPages(filterproducts.getTotalPages());
        productResponse.setLastPage(filterproducts.isLast());

        return productResponse;
    }
}
