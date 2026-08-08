package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ==========================
    // SEARCH
    // ==========================

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryId(Long categoryId);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );

    Page<Product> findByCategoryId(
            Long categoryId,
            Pageable pageable
    );

    // ==========================
    // INVENTORY
    // ==========================

    long countByStockLessThanEqual(Integer stock);

    List<Product> findByStockGreaterThan(Integer stock);

    List<Product> findByStockLessThan(Integer stock);

    // ✅ Inventory Module
    List<Product> findByStockLessThanEqual(Integer stock);

    // ✅ Out Of Stock
    List<Product> findByStock(Integer stock);

    // ==========================
    // PRICE FILTER
    // ==========================

    List<Product> findByPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    List<Product> findByCategoryIdAndPriceBetween(
            Long categoryId,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    // ==========================
    // LATEST PRODUCTS
    // ==========================

    List<Product> findTop10ByOrderByIdDesc();

}