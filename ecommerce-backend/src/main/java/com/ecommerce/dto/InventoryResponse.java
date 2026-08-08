package com.ecommerce.dto;

import com.ecommerce.entity.InventoryOperation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {

    private Long id;

    private Long productId;

    private String productName;

    private Integer previousStock;

    private Integer quantityChanged;

    private Integer newStock;

    private InventoryOperation operation;

    private String remarks;

    private String updatedBy;

    private LocalDateTime updatedAt;

}