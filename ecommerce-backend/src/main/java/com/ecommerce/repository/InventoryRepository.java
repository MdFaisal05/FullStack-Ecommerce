package com.ecommerce.repository;

import com.ecommerce.entity.Inventory;
import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository
        extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProduct(Product product);

    List<Inventory> findAllByOrderByUpdatedAtDesc();

}