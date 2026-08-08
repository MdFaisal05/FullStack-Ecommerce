package com.ecommerce.controller;

import com.ecommerce.dto.ProductRequest;
import com.ecommerce.dto.ProductResponse;
import com.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ==========================
    // ADD PRODUCT
    // ==========================

    @PostMapping
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody ProductRequest request) {

        return new ResponseEntity<>(
                productService.addProduct(request),
                HttpStatus.CREATED
        );
    }

    // ==========================
    // GET ALL PRODUCTS
    // ==========================

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }

    // ==========================
    // GET PRODUCT BY ID
    // ==========================

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    // ==========================
    // UPDATE PRODUCT
    // ==========================

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    // ==========================
    // DELETE PRODUCT
    // ==========================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                "Product Deleted Successfully"
        );
    }

    // ==========================
    // SEARCH PRODUCT
    // ==========================

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                productService.searchProducts(keyword)
        );
    }

    // ==========================
    // CATEGORY PRODUCTS
    // ==========================

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Long categoryId) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId)
        );
    }

    // ==========================
    // PAGINATION + SORTING
    // ==========================

    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponse>> getProducts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(

                productService.getProducts(
                        page,
                        size,
                        sortBy,
                        direction
                )

        );
    }

    // ==========================
    // SEARCH + PAGINATION
    // ==========================

    @GetMapping("/search/page")
    public ResponseEntity<Page<ProductResponse>> searchProducts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(

                productService.searchProducts(
                        keyword,
                        page,
                        size
                )

        );
    }

    // ==========================
    // CATEGORY + PAGINATION
    // ==========================

    @GetMapping("/category/{categoryId}/page")
    public ResponseEntity<Page<ProductResponse>> getCategoryProducts(

            @PathVariable Long categoryId,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(

                productService.getProductsByCategory(
                        categoryId,
                        page,
                        size
                )

        );
    }

    // ==========================
    // PRICE FILTER
    // ==========================

    @GetMapping("/filter/price")
    public ResponseEntity<List<ProductResponse>> filterByPrice(

            @RequestParam BigDecimal minPrice,

            @RequestParam BigDecimal maxPrice) {

        return ResponseEntity.ok(

                productService.getProductsByPriceRange(
                        minPrice,
                        maxPrice
                )

        );
    }

    // ==========================
    // CATEGORY + PRICE
    // ==========================

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponse>> filterByCategoryAndPrice(

            @RequestParam Long categoryId,

            @RequestParam BigDecimal minPrice,

            @RequestParam BigDecimal maxPrice) {

        return ResponseEntity.ok(

                productService.getProductsByCategoryAndPrice(
                        categoryId,
                        minPrice,
                        maxPrice
                )

        );
    }

    // ==========================
    // AVAILABLE PRODUCTS
    // ==========================

    @GetMapping("/available")
    public ResponseEntity<List<ProductResponse>> getAvailableProducts() {

        return ResponseEntity.ok(
                productService.getAvailableProducts()
        );
    }

    // ==========================
    // LATEST PRODUCTS
    // ==========================

    @GetMapping("/latest")
    public ResponseEntity<List<ProductResponse>> getLatestProducts() {

        return ResponseEntity.ok(
                productService.getLatestProducts()
        );
    }

}