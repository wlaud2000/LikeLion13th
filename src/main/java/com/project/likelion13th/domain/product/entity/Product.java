package com.project.likelion13th.domain.product.entity;


import com.project.likelion13th.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "price")
    private Double price;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    // soft delete를 위한 필드 추가
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 상품 정보 업데이트 메서드
    public void updateProduct(String name, String description, String profileImage, Double price, ProductType productType) {
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.price = price;
        this.productType = productType;
    }

    // soft delete 메서드
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    // 삭제 여부 확인 메서드
    public boolean isDeleted() {
        return deletedAt != null;
    }
}
