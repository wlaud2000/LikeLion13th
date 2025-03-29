package com.project.likelion13th.domain.review.controller;

import com.project.likelion13th.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;
import com.project.likelion13th.domain.review.service.command.ReviewCommandService;
import com.project.likelion13th.domain.review.service.query.ReviewQueryService;
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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Review API", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewCommandService reviewCommandService;

    @GetMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 목록 조회", description = "특정 상품의 리뷰 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 목록 조회 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewListDTO.class)))
    })
    public CustomResponse<ReviewResDTO.ReviewListDTO> getReviews(
            @PathVariable Long productId,
            @RequestParam Integer size,
            @RequestParam Long cursor
    ) {
        ReviewResDTO.ReviewListDTO response = reviewQueryService.getReviewPage(productId, cursor, size);
        return CustomResponse.onSuccess(response);
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 상세 조회", description = "특정 상품의 리뷰 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 상세 조회 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailDTO.class)))
    })
    public CustomResponse<ReviewResDTO.ReviewDetailDTO> getReview(@PathVariable Long reviewId) {
        ReviewResDTO.ReviewDetailDTO response = reviewQueryService.getReview(reviewId);
        return CustomResponse.onSuccess(response);
    }

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 작성", description = "특정 상품에 대한 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 작성 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailDTO.class)))
    })
    public CustomResponse<ReviewResDTO.ReviewDetailDTO> createReview(@PathVariable Long productId,
                                                                     @RequestBody ReviewReqDTO.CreateReviewDTO createReviewDTO,
                                                                     @AuthenticationPrincipal UserDetails userDetails) {
        ReviewResDTO.ReviewDetailDTO response = reviewCommandService.createReview(userDetails.getUsername(), productId, createReviewDTO);
        return CustomResponse.onSuccess(response);
    }

    @PatchMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "작성한 리뷰를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailDTO.class)))
    })
    public CustomResponse<ReviewResDTO.ReviewDetailDTO> updateReview(@PathVariable Long reviewId,
                                                                     @RequestBody ReviewReqDTO.UpdateReviewDTO updateReviewDTO,
                                                                     @AuthenticationPrincipal UserDetails userDetails) {
        ReviewResDTO.ReviewDetailDTO response = reviewCommandService.updateReview(userDetails.getUsername(), reviewId, updateReviewDTO);
        return CustomResponse.onSuccess(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "작성한 리뷰를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공")
    })
    public CustomResponse<String> deleteReview(@PathVariable Long reviewId,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        reviewCommandService.deleteReview(userDetails.getUsername(), reviewId);
        return CustomResponse.onSuccess("리뷰 삭제 성공");
    }

    @GetMapping("/reviews/me")
    @Operation(summary = "내 리뷰 조회", description = "현재 로그인한 사용자의 리뷰 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 리뷰 조회 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewListDTO.class)))
    })
    public CustomResponse<ReviewResDTO.ReviewListDTO> getMyReviews(
            @RequestParam Integer size,
            @RequestParam Long cursor,
            @AuthenticationPrincipal UserDetails userDetails) {
        ReviewResDTO.ReviewListDTO response = reviewQueryService.getMyReview(userDetails.getUsername(), cursor, size);
        return CustomResponse.onSuccess(response);
    }
}
