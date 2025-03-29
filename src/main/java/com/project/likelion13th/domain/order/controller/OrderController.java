package com.project.likelion13th.domain.order.controller;

import com.project.likelion13th.domain.order.dto.request.OrderReqDTO;
import com.project.likelion13th.domain.order.dto.response.OrderResDTO;
import com.project.likelion13th.domain.order.service.command.OrderCommandService;
import com.project.likelion13th.domain.order.service.query.OrderQueryService;
import com.project.likelion13th.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 관련 API")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;

    @PostMapping
    @Operation(summary = "주문 추가 (상품 구매)", description = "상품을 주문합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 추가 성공", content = @Content(schema = @Schema(implementation = OrderResDTO.OrderDetailDTO.class)))
    })
    public CustomResponse<OrderResDTO.OrderDetailDTO> createOrder(@RequestBody OrderReqDTO.CreateOrderDTO request,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {

        OrderResDTO.OrderDetailDTO response = orderCommandService.createOrder(userDetails.getUsername(), request);

        return CustomResponse.onSuccess(response);
    }

    @GetMapping("/me")
    @Operation(summary = "내 주문 조회", description = "사용자의 주문 목록을 커서 기반 페이지네이션으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 주문 조회 성공", content = @Content(schema = @Schema(implementation = OrderResDTO.OrderListDTO.class)))
    })
    public CustomResponse<OrderResDTO.OrderListDTO> getMyOrders(
            @RequestParam Long cursor,
            @RequestParam Integer size,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        OrderResDTO.OrderListDTO response = orderQueryService.getMyOrder(userDetails.getUsername(), cursor, size);
        return CustomResponse.onSuccess(response);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 상태 변경 성공", content = @Content(schema = @Schema(implementation = OrderResDTO.OrderDetailDTO.class)))
    })
    public CustomResponse<OrderResDTO.OrderDetailDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderReqDTO.UpdateOrderStatusDTO request,
            @AuthenticationPrincipal UserDetails userDetails) {
        OrderResDTO.OrderDetailDTO response = orderCommandService.updateOrderStatus(userDetails.getUsername(), orderId, request);
        return CustomResponse.onSuccess(response);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 취소 성공")
    })
    public CustomResponse<String> cancelOrder(@PathVariable Long orderId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        orderCommandService.cancelOrder(userDetails.getUsername(), orderId);
        return CustomResponse.onSuccess("주문 취소 성공");
    }
}
