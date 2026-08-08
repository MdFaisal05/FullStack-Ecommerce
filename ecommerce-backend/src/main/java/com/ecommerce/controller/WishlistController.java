package com.ecommerce.controller;

import com.ecommerce.dto.WishlistResponse;
import com.ecommerce.service.WishlistService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER','ADMIN')")
public class WishlistController {

    private final WishlistService wishlistService;

    // ============================================
    // Add Product To Wishlist
    // POST : /api/wishlist/{productId}
    // ============================================

    @PostMapping("/{productId}")
    public ResponseEntity<WishlistResponse> addToWishlist(

            @PathVariable Long productId

    ) {

        return ResponseEntity.ok(

                wishlistService.addToWishlist(productId)

        );

    }

    // ============================================
    // Remove Product From Wishlist
    // DELETE : /api/wishlist/{productId}
    // ============================================

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromWishlist(

            @PathVariable Long productId

    ) {

        return ResponseEntity.ok(

                wishlistService.removeFromWishlist(productId)

        );

    }

    // ============================================
    // Get Logged In User Wishlist
    // GET : /api/wishlist
    // ============================================

    @GetMapping
    public ResponseEntity<List<WishlistResponse>> getMyWishlist() {

        return ResponseEntity.ok(

                wishlistService.getMyWishlist()

        );

    }

}