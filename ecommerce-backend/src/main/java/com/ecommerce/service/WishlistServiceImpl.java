package com.ecommerce.service;

import com.ecommerce.dto.WishlistResponse;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.entity.Wishlist;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    // ==========================================
    // Logged In User
    // ==========================================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

    }

    // ==========================================
    // Wishlist -> DTO
    // ==========================================

    private WishlistResponse mapWishlist(Wishlist wishlist) {

        Product product = wishlist.getProduct();

        return WishlistResponse.builder()

                .wishlistId(wishlist.getId())

                .productId(product.getId())

                .productName(product.getName())

                .imageUrl(product.getImageUrl())

                .price(product.getPrice())

                .build();

    }

    // ==========================================
    // Add To Wishlist
    // ==========================================

    @Override
    public WishlistResponse addToWishlist(Long productId) {

        User user = getLoggedInUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Not Found"));

        wishlistRepository.findByUserAndProduct(user, product)
                .ifPresent(w -> {
                    throw new RuntimeException("Product Already In Wishlist");
                });

        Wishlist wishlist = Wishlist.builder()

                .user(user)

                .product(product)

                .build();

        Wishlist savedWishlist = wishlistRepository.save(wishlist);

        return mapWishlist(savedWishlist);

    }

    // ==========================================
    // Remove From Wishlist
    // ==========================================

    @Override
    public String removeFromWishlist(Long productId) {

        User user = getLoggedInUser();

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Not Found"));

        Wishlist wishlist = wishlistRepository
                .findByUserAndProduct(user, product)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Wishlist Item Not Found"));

        wishlistRepository.delete(wishlist);

        return "Product Removed From Wishlist Successfully";

    }

    // ==========================================
    // My Wishlist
    // ==========================================

    @Override
    public List<WishlistResponse> getMyWishlist() {

        User user = getLoggedInUser();

        return wishlistRepository.findByUser(user)
                .stream()
                .map(this::mapWishlist)
                .toList();

    }

}