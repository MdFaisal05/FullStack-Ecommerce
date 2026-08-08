package com.ecommerce.controller;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;
import com.ecommerce.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // ===========================
    // Stock In / Stock Out
    // ===========================

    @PostMapping("/update")
    public ResponseEntity<InventoryResponse> updateInventory(

            @Valid @RequestBody InventoryRequest request) {

        return ResponseEntity.ok(

                inventoryService.updateStock(request)

        );
    }

    // ===========================
    // Manual Stock Update
    // ===========================

    @PutMapping("/update/{productId}")
    public ResponseEntity<String> updateStock(

            @PathVariable Long productId,

            @RequestParam Integer stock) {

        return ResponseEntity.ok(

                inventoryService.updateStock(productId, stock)

        );
    }

    // ===========================
    // Inventory History
    // ===========================

    @GetMapping("/history")
    public ResponseEntity<List<InventoryResponse>> getInventoryHistory() {

        return ResponseEntity.ok(

                inventoryService.getInventoryHistory()

        );
    }

    // ===========================
    // Product Inventory History
    // ===========================

    @GetMapping("/history/{productId}")
    public ResponseEntity<List<InventoryResponse>> getProductHistory(

            @PathVariable Long productId) {

        return ResponseEntity.ok(

                inventoryService.getProductHistory(productId)

        );
    }

    // ===========================
    // Low Stock Products
    // ===========================

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventoryResponse>> getLowStockProducts() {

        return ResponseEntity.ok(

                inventoryService.getLowStockProducts()

        );
    }

    // ===========================
    // Out Of Stock Products
    // ===========================

    @GetMapping("/out-of-stock")
    public ResponseEntity<List<InventoryResponse>> getOutOfStockProducts() {

        return ResponseEntity.ok(

                inventoryService.getOutOfStockProducts()

        );
    }

}