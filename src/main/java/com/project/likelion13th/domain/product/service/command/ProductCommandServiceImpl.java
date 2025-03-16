package com.project.likelion13th.domain.product.service.command;

import com.project.likelion13th.domain.product.converter.ProductConverter;
import com.project.likelion13th.domain.product.dto.request.ProductReqDTO;
import com.project.likelion13th.domain.product.dto.response.ProductResDTO;
import com.project.likelion13th.domain.product.entity.Product;
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
        Product product = ProductConverter.toEntity(dto);
        productRepository.save((product));

        return ProductConverter.from(product);
    }


}
