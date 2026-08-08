package com.ecommerce.service;

import com.ecommerce.dto.WishlistResponse;

import java.util.List;

public interface WishlistService {

    WishlistResponse addToWishlist(Long productId);

    String removeFromWishlist(Long productId);

    List<WishlistResponse> getMyWishlist();

}