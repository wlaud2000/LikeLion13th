package com.project.likelion13th.domain.review.service.query;

import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;

public interface ReviewQueryService {
    ReviewResDTO.ReviewDetailDTO getReview(Long reviewId);
    ReviewResDTO.ReviewListDTO getReviewPage(Long productId, Long cursor, int size);
    ReviewResDTO.ReviewListDTO getMyReview(String email, Long cursor, int size);

}
