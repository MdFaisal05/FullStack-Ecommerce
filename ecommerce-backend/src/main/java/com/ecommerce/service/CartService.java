package com.ecommerce.service;

import com.ecommerce.dto.AddToCartRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.dto.UpdateCartRequest;

public interface CartService {

    // ===============================
    // Add Product To Cart
    // ===============================
    CartResponse addToCart(AddToCartRequest request);

    // ===============================
    // Get Logged In User Cart
    // ===============================
    CartResponse getCart();

    // ===============================
    // Update Quantity
    // ===============================
    CartResponse updateCartItem(
            Long cartItemId,
            UpdateCartRequest request
    );

    // ===============================
    // Remove Item From Cart
    // ===============================
    String removeCartItem(Long cartItemId);

    // ===============================
    // Clear Complete Cart
    // ===============================
    String clearCart();

}