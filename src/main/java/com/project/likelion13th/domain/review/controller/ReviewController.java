package com.project.likelion13th.domain.review.controller;

import com.project.likelion13th.domain.review.dto.request.ReviewReqDTO;
import com.project.likelion13th.domain.review.dto.response.ReviewResDTO;
import com.project.likelion13th.domain.review.service.command.ReviewCommandService;
import com.project.likelion13th.domain.review.service.query.ReviewQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReviewResDTO.ReviewListDTO> getReviews(
            @PathVariable Long productId,
            @RequestParam Integer size,
            @RequestParam Long cursor
    ) {
        ReviewResDTO.ReviewListDTO response = reviewQueryService.getReviewPage(productId, cursor, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 상세 조회", description = "특정 상품의 리뷰 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 상세 조회 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailDTO.class)))
    })
    public ResponseEntity<ReviewResDTO.ReviewDetailDTO> getReview(@PathVariable Long reviewId) {
        ReviewResDTO.ReviewDetailDTO response = reviewQueryService.getReview(reviewId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products/{productId}/reviews")
    @Operation(summary = "리뷰 작성", description = "특정 상품에 대한 리뷰를 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 작성 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailDTO.class)))
    })
    public ResponseEntity<ReviewResDTO.ReviewDetailDTO> createReview(@PathVariable Long productId, @RequestBody ReviewReqDTO.CreateReviewDTO createReviewDTO) {
        ReviewResDTO.ReviewDetailDTO response = reviewCommandService.createReview(productId, createReviewDTO);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "작성한 리뷰를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 수정 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewDetailDTO.class)))
    })
    public ResponseEntity<ReviewResDTO.ReviewDetailDTO> updateReview(@PathVariable Long reviewId, @RequestBody ReviewReqDTO.UpdateReviewDTO updateReviewDTO) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "작성한 리뷰를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공")
    })
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/reviews/me")
    @Operation(summary = "내 리뷰 조회", description = "현재 로그인한 사용자의 리뷰 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "내 리뷰 조회 성공", content = @Content(schema = @Schema(implementation = ReviewResDTO.ReviewListDTO.class)))
    })
    public ResponseEntity<ReviewResDTO.ReviewListDTO> getMyReviews(
            @RequestParam Integer size,
            @RequestParam Long cursor) {
        ReviewResDTO.ReviewListDTO response = reviewQueryService.getMyReview(cursor, size);
        return ResponseEntity.ok(response);
    }
}
