package com.project.likelion13th.domain.product.controller;

import com.project.likelion13th.domain.product.dto.request.ProductReqDTO;
import com.project.likelion13th.domain.product.dto.response.ProductResDTO;
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
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "상품 관련 API")
public class ProductController {

    @GetMapping
    @Operation(summary = "상품 목록 조회", description = "카테고리별로 상품 목록을 무한 스크롤 방식으로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공", content = @Content(schema = @Schema(implementation = ProductResDTO.ProductListDTO.class)))
    })
    public ResponseEntity<ProductResDTO.ProductListDTO> getProducts(
            @RequestParam String category,
            @RequestParam Long cursor,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 조회", description = "특정 상품의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공", content = @Content(schema = @Schema(implementation = ProductResDTO.ProductDetailDTO.class)))
    })
    public ResponseEntity<ProductResDTO.ProductDetailDTO> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    @Operation(summary = "상품 추가", description = "새로운 상품을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 추가 성공", content = @Content(schema = @Schema(implementation = ProductResDTO.ProductDetailDTO.class)))
    })
    public ResponseEntity<ProductResDTO.ProductDetailDTO> addProduct(@RequestBody ProductReqDTO.CreateProductDTO createProductDTO) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제", description = "특정 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(null);
    }
}
