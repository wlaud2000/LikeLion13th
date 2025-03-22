package com.project.likelion13th.domain.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ProductReqDTO {

    @Getter
    public static class CreateProductDTO {
        @Schema(description = "상품명", example = "Minimal Stand")
        @NotBlank(message = "상품명은 필수 입력값입니다.")
        private String name;

        @Schema(description = "상품 가격", example = "50")
        @NotNull(message = "상품 가격은 필수 입력값입니다.")
        private Double price;

        @Schema(description = "상품 설명", example = "천연 원목으로 제작된 미니멀 디자인 스탠드입니다.")
        @NotBlank(message = "상품 설명은 필수 입력값입니다.")
        private String description;

        @Schema(description = "상품 카테고리", example = "Furniture")
        @NotBlank(message = "카테고리는 필수 입력값입니다.")
        private String productType;

        @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
        @NotBlank(message = "상품 이미지는 필수 입력값입니다.")
        private String imageUrl;
    }

    @Getter
    public static class UpdateProductDTO {
        @Schema(description = "상품명", example = "Updated Minimal Stand")
        @NotBlank(message = "상품명은 필수 입력값입니다.")
        private String name;

        @Schema(description = "상품 가격", example = "60")
        @NotNull(message = "상품 가격은 필수 입력값입니다.")
        private Double price;

        @Schema(description = "상품 설명", example = "업데이트된 천연 원목으로 제작된 미니멀 디자인 스탠드입니다.")
        @NotBlank(message = "상품 설명은 필수 입력값입니다.")
        private String description;

        @Schema(description = "상품 카테고리", example = "LAMP")
        @NotBlank(message = "카테고리는 필수 입력값입니다.")
        private String productType;

        @Schema(description = "상품 이미지 URL", example = "https://example.com/updated-image.jpg")
        @NotBlank(message = "상품 이미지는 필수 입력값입니다.")
        private String imageUrl;
    }
}
