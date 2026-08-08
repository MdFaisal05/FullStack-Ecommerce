package com.ecommerce.service;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {

    // Inventory History

    List<InventoryResponse> getInventoryHistory();

    List<InventoryResponse> getProductHistory(Long productId);

    // Stock Update with History

    InventoryResponse updateStock(InventoryRequest request);

    // Admin Direct Stock Update

    String updateStock(Long productId, Integer stock);

    // Reports

    List<InventoryResponse> getLowStockProducts();

    List<InventoryResponse> getOutOfStockProducts();

}