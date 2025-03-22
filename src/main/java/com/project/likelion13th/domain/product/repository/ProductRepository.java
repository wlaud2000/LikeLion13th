package com.project.likelion13th.domain.product.repository;

import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.product.entity.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 첫 페이지 조회 (삭제되지 않은 상품만)
    @Query("SELECT p FROM Product p WHERE p.productType = :category AND p.deletedAt IS NULL ORDER BY p.id DESC")
    Slice<Product> findFirstPage(@Param("category") ProductType category, Pageable pageable);

    // cursor 기반 조회 (삭제되지 않은 상품만)
    @Query("SELECT p FROM Product p WHERE p.productType = :category AND p.id < :cursor AND p.deletedAt IS NULL ORDER BY p.id DESC")
    Slice<Product> findByCursor(@Param("category") ProductType category, @Param("cursor") Long cursor, Pageable pageable);

    // 삭제되지 않은 상품 ID로 조회
    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<Product> findByIdAndNotDeleted(@Param("id") Long id);
}
