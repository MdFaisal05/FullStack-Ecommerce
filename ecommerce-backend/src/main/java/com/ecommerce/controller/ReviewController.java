package com.ecommerce.controller;

import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ReviewResponse;
import com.ecommerce.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Add Review
    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @Valid @RequestBody ReviewRequest request) {

        return ResponseEntity.ok(
                reviewService.addReview(request)
        );
    }

    // Get Reviews of Product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getReviewsByProduct(productId)
        );
    }

    // Average Rating
    @GetMapping("/product/{productId}/rating")
    public ResponseEntity<Double> getAverageRating(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getAverageRating(productId)
        );
    }

    // Total Reviews
    @GetMapping("/product/{productId}/count")
    public ResponseEntity<Long> getTotalReviews(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                reviewService.getTotalReviews(productId)
        );
    }
}