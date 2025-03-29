package com.project.likelion13th.domain.review.service.command;

import com.project.likelion13th.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;

public interface ReviewCommandService {
    ReviewResDTO.ReviewDetailDTO createReview(String email, Long productId, ReviewReqDTO.CreateReviewDTO dto);
    ReviewResDTO.ReviewDetailDTO updateReview(String email, Long reviewId, ReviewReqDTO.UpdateReviewDTO dto);
    void deleteReview(String email, Long reviewId);
}
