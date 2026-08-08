package com.ecommerce.service;

import com.ecommerce.dto.ReviewRequest;
import com.ecommerce.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse addReview(ReviewRequest request);

    List<ReviewResponse> getReviewsByProduct(Long productId);

    Double getAverageRating(Long productId);

    Long getTotalReviews(Long productId);
}