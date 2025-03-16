package com.project.likelion13th.domain.product.converter;

import com.project.likelion13th.domain.product.dto.request.ProductReqDTO;
import com.project.likelion13th.domain.product.dto.response.ProductResDTO;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.entity.ProductType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductConverter {

    public static ProductResDTO.ProductListDTO from(Slice<Product> products){
        return ProductResDTO.ProductListDTO.builder()
                .products(products.stream().map(ProductConverter::from).toList())
                .hasNext(products.hasNext())
                .nextCursor(products.isEmpty() ?
                        0L :
                        products.getContent().get(products.getContent().size() - 1).getId())
                .build();
    }

    public static ProductResDTO.ProductDetailDTO from(Product product){
        return ProductResDTO.ProductDetailDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .productType(product.getProductType().name())
                .imageUrl(product.getProfileImage())
                .build();
    }

    public static Product toEntity(ProductReqDTO.CreateProductDTO dto){
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .profileImage(dto.getImageUrl())
                .productType(ProductType.valueOf(dto.getProductType().toUpperCase()))
                .build();
    }
}
