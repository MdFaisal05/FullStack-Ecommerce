package com.ecommerce.controller;

import com.ecommerce.dto.AddToCartRequest;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.dto.UpdateCartRequest;
import com.ecommerce.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ============================================
    // Add Product To Cart
    // POST : /api/cart/add
    // ============================================

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @Valid @RequestBody AddToCartRequest request
    ) {

        return ResponseEntity.ok(
                cartService.addToCart(request)
        );

    }

    // ============================================
    // Get Logged In User Cart
    // GET : /api/cart
    // ============================================

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {

        return ResponseEntity.ok(
                cartService.getCart()
        );

    }

    // ============================================
    // Update Cart Item Quantity
    // PUT : /api/cart/{cartItemId}
    // ============================================

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartResponse> updateCartItem(

            @PathVariable Long cartItemId,

            @Valid
            @RequestBody
            UpdateCartRequest request

    ) {

        return ResponseEntity.ok(

                cartService.updateCartItem(
                        cartItemId,
                        request
                )

        );

    }

    // ============================================
    // Remove Cart Item
    // DELETE : /api/cart/{cartItemId}
    // ============================================

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> removeCartItem(

            @PathVariable Long cartItemId

    ) {

        return ResponseEntity.ok(

                cartService.removeCartItem(
                        cartItemId
                )

        );

    }

    // ============================================
    // Clear Complete Cart
    // DELETE : /api/cart/clear
    // ============================================

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {

        return ResponseEntity.ok(

                cartService.clearCart()

        );

    }

}