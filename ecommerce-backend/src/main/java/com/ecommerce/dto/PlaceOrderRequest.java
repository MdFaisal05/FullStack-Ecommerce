package com.ecommerce.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderRequest {

    @Valid
    @NotNull(message = "Shipping Address is required")
    private ShippingAddressRequest shippingAddress;

}