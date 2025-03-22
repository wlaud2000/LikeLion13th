package com.project.likelion13th.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderReqDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CreateOrderDTO {
        @Schema(description = "상품 ID", example = "123")
        @NotNull(message = "상품 ID는 필수 입력값입니다.")
        private Long productId;

        @Schema(description = "주문 수량", example = "2")
        @NotNull(message = "주문 수량은 필수 입력값입니다.")
        private Integer quantity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateOrderStatusDTO {
        @Schema(description = "주문 상태", example = "DELIVERED")
        @NotNull(message = "주문 상태는 필수 입력값입니다.")
        private String orderStatus;
    }
}
