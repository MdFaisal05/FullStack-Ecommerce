package com.ecommerce.service;

import com.ecommerce.dto.AddToCartRequest;
import com.ecommerce.dto.CartItemResponse;
import com.ecommerce.dto.CartResponse;
import com.ecommerce.dto.UpdateCartRequest;
import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    // =====================================================
    // Step 9.9 Part 6.2 se methods start honge
    // =====================================================


    // =====================================================
    // Helper Method
    // Get Logged In User
    // =====================================================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

    }


    // =====================================================
    // Helper Method
    // Get Or Create Cart
    // =====================================================

    private Cart getOrCreateCart(User user) {

        return cartRepository.findByUser(user)

                .orElseGet(() -> {

                    Cart cart = new Cart();

                    cart.setUser(user);

                    return cartRepository.save(cart);

                });

    }


    // =====================================================
    // Helper Method
    // Calculate Total Amount
    // =====================================================

    private BigDecimal calculateTotal(Cart cart) {

        List<CartItem> items =
                cartItemRepository.findByCart(cart);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {

            BigDecimal subtotal =
                    item.getProduct()
                            .getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()
                                    )
                            );

            total = total.add(subtotal);

        }

        return total;

    }


    // =====================================================
    // Helper Method
    // Convert Cart -> CartResponse
    // =====================================================

    private CartResponse mapToResponse(Cart cart) {

        List<CartItem> cartItems =
                cartItemRepository.findByCart(cart);

        List<CartItemResponse> itemResponses =
                new ArrayList<>();

        int totalItems = 0;

        for (CartItem item : cartItems) {

            BigDecimal subtotal =
                    item.getProduct()
                            .getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()
                                    )
                            );

            CartItemResponse response =
                    CartItemResponse.builder()

                            .cartItemId(item.getId())

                            .productId(item.getProduct().getId())

                            .productName(item.getProduct().getName())

                            .imageUrl(item.getProduct().getImageUrl())

                            .price(item.getProduct().getPrice())

                            .quantity(item.getQuantity())

                            .subtotal(subtotal)

                            .build();

            itemResponses.add(response);

            totalItems += item.getQuantity();

        }

        return CartResponse.builder()

                .cartId(cart.getId())

                .userId(cart.getUser().getId())

                .items(itemResponses)

                .totalItems(totalItems)

                .totalAmount(calculateTotal(cart))

                .build();

    }


// =====================================================
// Add Product To Cart
// =====================================================

    @Override
    public CartResponse addToCart(AddToCartRequest request) {

        // Logged In User
        User user = getLoggedInUser();

        // User Cart
        Cart cart = getOrCreateCart(user);

        // Product
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product Not Found"));

        // Check Product Already Exists In Cart
        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {

            // Increase Quantity
            cartItem.setQuantity(
                    cartItem.getQuantity() + request.getQuantity()
            );

        } else {

            // Create New Cart Item
            cartItem = new CartItem();

            cartItem.setCart(cart);

            cartItem.setProduct(product);

            cartItem.setQuantity(request.getQuantity());

        }

        cartItemRepository.save(cartItem);

        return mapToResponse(cart);

    }

    // =====================================================
// Get Logged In User Cart
// =====================================================

    @Override
    public CartResponse getCart() {

        // Logged In User
        User user = getLoggedInUser();

        // Get Existing Cart or Create New Cart
        Cart cart = getOrCreateCart(user);

        // Return Cart Response
        return mapToResponse(cart);

    }

    // =====================================================
// Update Cart Item Quantity
// =====================================================

    @Override
    public CartResponse updateCartItem(
            Long cartItemId,
            UpdateCartRequest request
    ) {

        // Logged In User
        User user = getLoggedInUser();

        // User Cart
        Cart cart = getOrCreateCart(user);

        // Find Cart Item
        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Item Not Found"));

        // Security Check
        if (!cartItem.getCart().getId().equals(cart.getId())) {

            throw new RuntimeException(
                    "You cannot update another user's cart."
            );

        }

        // Quantity Validation
        if (request.getQuantity() <= 0) {

            throw new RuntimeException(
                    "Quantity must be greater than zero."
            );

        }

        // Update Quantity
        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        // Return Updated Cart
        return mapToResponse(cart);

    }
    // =====================================================
// Remove Item From Cart
// =====================================================

    @Override
    public String removeCartItem(Long cartItemId) {

        // Logged In User
        User user = getLoggedInUser();

        // User Cart
        Cart cart = getOrCreateCart(user);

        // Find Cart Item
        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cart Item Not Found"));

        // Security Check
        if (!cartItem.getCart().getId().equals(cart.getId())) {

            throw new RuntimeException(
                    "You cannot remove another user's cart item."
            );

        }

        // Delete Cart Item
        cartItemRepository.delete(cartItem);

        return "Cart Item Removed Successfully";

    }

    // =====================================================
// Clear Complete Cart
// =====================================================

    @Override
    public String clearCart() {

        // Logged In User
        User user = getLoggedInUser();

        // User Cart
        Cart cart = getOrCreateCart(user);

        // Get All Cart Items
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        // Delete All Items
        cartItemRepository.deleteAll(cartItems);

        return "Cart Cleared Successfully";

    }
}

