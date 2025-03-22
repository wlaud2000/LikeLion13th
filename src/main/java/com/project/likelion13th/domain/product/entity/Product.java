package com.project.likelion13th.domain.product.entity;


import com.project.likelion13th.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
