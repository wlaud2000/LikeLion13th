package com.project.likelion13th.domain.review.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ReviewResDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListDTO {
        @Schema(description = "커서 ID (다음 페이지 조회를 위한 커서)", example = "100")
        private Long nextCursor;

        @Schema(description = "마지막 여부", example = "true")
        private Boolean hasNext;

        @Schema(description = "리뷰 목록")
        private List<ReviewDetailDTO> reviews;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDetailDTO {
        @Schema(description = "리뷰 ID", example = "123")
        private Long reviewId;

        @Schema(description = "상품 ID", example = "456")
        private Long productId;

        @Schema(description = "리뷰 내용", example = "이 제품 정말 좋아요!")
        private String content;

        @Schema(description = "평점", example = "4.5")
        private Double rating;

        @Schema(description = "작성자", example = "John Doe")
        private String author;

        @Schema(description = "작성일", example = "2025-03-02")
        private String createdAt;
    }
}
