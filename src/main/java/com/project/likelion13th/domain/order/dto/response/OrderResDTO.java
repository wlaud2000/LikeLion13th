package com.project.likelion13th.domain.order.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderResDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderListDTO {
        @Schema(description = "커서 ID (다음 페이지 조회를 위한 커서)", example = "50")
        private Long nextCursor;

        @Schema(description = "마지막 여부", example = "true")
        private Boolean hasNext;

        @Schema(description = "주문 목록")
        private List<OrderDetailDTO> orders;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OrderDetailDTO {
        @Schema(description = "주문 ID", example = "456")
        private Long orderId;

        @Schema(description = "상품 ID", example = "123")
        private Long productId;

        @Schema(description = "주문 수량", example = "2")
        private Integer quantity;

        @Schema(description = "총 가격", example = "100.00")
        private Double totalPrice;

        @Schema(description = "주문 상태", example = "CONFIRMED")
        private String status;

        @Schema(description = "주문 날짜", example = "2025-03-02")
        private String orderDate;
    }
}
