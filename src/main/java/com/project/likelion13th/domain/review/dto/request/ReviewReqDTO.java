package com.project.likelion13th.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReviewReqDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateReviewDTO {
        @Schema(description = "리뷰 내용", example = "이 제품 정말 마음에 들어요!")
        @NotBlank(message = "리뷰 내용은 필수 입력값입니다.")
        private String content;

        @Schema(description = "평점", example = "4.5")
        @NotNull(message = "평점은 필수 입력값입니다.")
        private Double rating;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateReviewDTO {
        @Schema(description = "리뷰 내용", example = "이 제품 정말 마음에 들어요!")
        @NotBlank(message = "리뷰 내용은 필수 입력값입니다.")
        private String content;

        @Schema(description = "평점", example = "4.5")
        @NotNull(message = "평점은 필수 입력값입니다.")
        private Double rating;
    }
}
