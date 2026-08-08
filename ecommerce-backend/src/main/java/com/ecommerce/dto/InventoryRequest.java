package com.ecommerce.dto;

import com.ecommerce.entity.InventoryOperation;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private InventoryOperation operation;

    private String remarks;

}