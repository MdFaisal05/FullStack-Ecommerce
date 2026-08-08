package com.ecommerce.service;

import com.ecommerce.dto.InventoryRequest;
import com.ecommerce.dto.InventoryResponse;
import com.ecommerce.entity.Inventory;
import com.ecommerce.entity.InventoryOperation;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.InventoryRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    @Override
    public InventoryResponse updateStock(InventoryRequest request) {

        Product product = productRepository.findById(request.getProductId())

                .orElseThrow(() ->
                        new RuntimeException("Product Not Found"));

        int previousStock = product.getStock();

        int newStock = previousStock;

        if (request.getOperation() == InventoryOperation.STOCK_IN) {

            newStock += request.getQuantity();

        } else if (request.getOperation() == InventoryOperation.STOCK_OUT) {

            if (previousStock < request.getQuantity()) {

                throw new RuntimeException("Insufficient Stock");

            }

            newStock -= request.getQuantity();

        } else {

            newStock = request.getQuantity();

        }

        product.setStock(newStock);

        productRepository.save(product);

        Inventory inventory = Inventory.builder()

                .product(product)

                .previousStock(previousStock)

                .quantityChanged(request.getQuantity())

                .newStock(newStock)

                .operation(request.getOperation())

                .remarks(request.getRemarks())

                .updatedBy("ADMIN")

                .build();

        inventoryRepository.save(inventory);

        return map(inventory);

    }

    @Override
    public String updateStock(Long productId, Integer stock) {

        Product product = productRepository.findById(productId)

                .orElseThrow(() ->
                        new RuntimeException("Product Not Found"));

        int previous = product.getStock();

        product.setStock(stock);

        productRepository.save(product);

        Inventory inventory = Inventory.builder()

                .product(product)

                .previousStock(previous)

                .quantityChanged(stock - previous)

                .newStock(stock)

                .operation(InventoryOperation.ADJUSTMENT)

                .remarks("Manual Update")

                .updatedBy("ADMIN")

                .build();

        inventoryRepository.save(inventory);

        return "Stock Updated Successfully";

    }

    @Override
    public List<InventoryResponse> getInventoryHistory() {

        return inventoryRepository.findAllByOrderByUpdatedAtDesc()

                .stream()

                .map(this::map)

                .collect(Collectors.toList());

    }

    @Override
    public List<InventoryResponse> getProductHistory(Long productId) {

        Product product = productRepository.findById(productId)

                .orElseThrow(() ->
                        new RuntimeException("Product Not Found"));

        return inventoryRepository.findByProduct(product)

                .stream()

                .map(this::map)

                .collect(Collectors.toList());

    }

    @Override
    public List<InventoryResponse> getLowStockProducts() {

        return productRepository

                .findByStockLessThanEqual(5)

                .stream()

                .map(product -> InventoryResponse.builder()

                        .productId(product.getId())

                        .productName(product.getName())

                        .newStock(product.getStock())

                        .build())

                .collect(Collectors.toList());

    }

    @Override
    public List<InventoryResponse> getOutOfStockProducts() {

        return productRepository

                .findByStock(0)

                .stream()

                .map(product -> InventoryResponse.builder()

                        .productId(product.getId())

                        .productName(product.getName())

                        .newStock(product.getStock())

                        .build())

                .collect(Collectors.toList());

    }

    private InventoryResponse map(Inventory inventory) {

        return InventoryResponse.builder()

                .id(inventory.getId())

                .productId(inventory.getProduct().getId())

                .productName(inventory.getProduct().getName())

                .previousStock(inventory.getPreviousStock())

                .quantityChanged(inventory.getQuantityChanged())

                .newStock(inventory.getNewStock())

                .operation(inventory.getOperation())

                .remarks(inventory.getRemarks())

                .updatedBy(inventory.getUpdatedBy())

                .updatedAt(inventory.getUpdatedAt())

                .build();

    }

}