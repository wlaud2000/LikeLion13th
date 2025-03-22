package com.project.likelion13th.domain.product.service.command;

import com.project.likelion13th.domain.product.converter.ProductConverter;
import com.project.likelion13th.domain.product.dto.request.ProductReqDTO;
import com.project.likelion13th.domain.product.dto.response.ProductResDTO;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.entity.ProductType;
import com.project.likelion13th.domain.product.exception.ProductErrorCode;
import com.project.likelion13th.domain.product.exception.ProductException;
import com.project.likelion13th.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCommandServiceImpl implements ProductCommandService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductDetailDTO createProduct(ProductReqDTO.CreateProductDTO dto) {
        Product product = ProductConverter.toProduct(dto);
        productRepository.save((product));

        return ProductConverter.toProductDetailDTO(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        // 상품 조회
        Product product = productRepository.findByIdAndNotDeleted(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        // 이미 주문된 상품인지 확인 (선택적으로 적용)
        // 실제 구현에서는 주문 여부 확인 로직이 필요할 수 있음
        // long orderCount = orderProductRepository.countByProductId(productId);
        // if (orderCount > 0) {
        //     throw new ProductException(ProductErrorCode.PRODUCT_IN_USE);
        // }

        // soft delete 처리
        product.delete();
        productRepository.save(product);
    }
}
