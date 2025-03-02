package com.project.likelion13th.domain.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ProductResDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductListDTO {
        @Schema(description = "마지막 여부", example = "true")
        private Boolean hasNext;

        @Schema(description = "다음 커서", example = "10")
        private Long nextCursor;

        @Schema(description = "상품 리스트")
        private List<ProductDetailDTO> products;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductDetailDTO {
        @Schema(description = "상품 ID", example = "123")
        private Long productId;

        @Schema(description = "상품명", example = "Minimal Stand")
        private String name;

        @Schema(description = "상품 가격", example = "50")
        private Double price;

        @Schema(description = "상품 설명", example = "천연 원목으로 제작된 미니멀 디자인 스탠드입니다.")
        private String description;

        @Schema(description = "상품 카테고리", example = "Furniture")
        private String productType;

        @Schema(description = "상품 이미지 URL", example = "https://example.com/image.jpg")
        private String imageUrl;
    }
}
