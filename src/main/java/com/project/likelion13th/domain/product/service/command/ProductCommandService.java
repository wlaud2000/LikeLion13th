package com.project.likelion13th.domain.product.service.command;

import com.project.likelion13th.domain.product.dto.request.ProductReqDTO;
import com.project.likelion13th.domain.product.dto.response.ProductResDTO;
import com.project.likelion13th.domain.product.entity.Product;
import org.springframework.data.domain.Slice;

public interface ProductCommandService {
    ProductResDTO.ProductDetailDTO createProduct(ProductReqDTO.CreateProductDTO dto);
    void deleteProduct(Long productId);
}
