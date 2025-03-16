package com.project.likelion13th.domain.product.service.query;

import com.project.likelion13th.domain.product.dto.response.ProductResDTO;

public interface ProductQueryService {
    ProductResDTO.ProductListDTO getProductPage(String category, Long cursor, int size);

    ProductResDTO.ProductDetailDTO getProduct(Long productId);
}
