package com.ecommerce.service;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    // CRUD
    ProductResponse addProduct(ProductRequest request);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    // Search
    List<ProductResponse> searchProducts(String keyword);

    // Category
    List<ProductResponse> getProductsByCategory(Long categoryId);

    // ==========================
    // PAGINATION
    // ==========================

    Page<ProductResponse> getProducts(int page,
                                      int size,
                                      String sortBy,
                                      String direction);

    Page<ProductResponse> searchProducts(String keyword,
                                         int page,
                                         int size);

    Page<ProductResponse> getProductsByCategory(Long categoryId,
                                                int page,
                                                int size);

    // ==========================
    // FILTERING
    // ==========================

    List<ProductResponse> getProductsByPriceRange(
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    List<ProductResponse> getProductsByCategoryAndPrice(
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    List<ProductResponse> getAvailableProducts();

    List<ProductResponse> getLatestProducts();
}