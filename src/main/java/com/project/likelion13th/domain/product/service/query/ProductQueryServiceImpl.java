package com.project.likelion13th.domain.product.service.query;

import ch.qos.logback.core.spi.ErrorCodes;
import com.project.likelion13th.domain.product.converter.ProductConverter;
import com.project.likelion13th.domain.product.dto.response.ProductResDTO;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.entity.ProductType;
import com.project.likelion13th.domain.product.exception.ProductErrorCode;
import com.project.likelion13th.domain.product.exception.ProductException;
import com.project.likelion13th.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public ProductResDTO.ProductListDTO getProductPage(String category, Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "id"));
        Slice<Product> products;

        ProductType productType = ProductType.valueOf(category.toUpperCase());

        if (cursor == null || cursor == 0) {
            products = productRepository.findFirstPage(productType, pageable);
        } else {
            products = productRepository.findByCursor(productType, cursor, pageable);
        }

        return ProductConverter.toProductListDTO(products);
    }

    @Override
    public ProductResDTO.ProductDetailDTO getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return ProductConverter.toProductDetailDTO(product);
    }
}
