package com.project.likelion13th.domain.product.repository;

import com.project.likelion13th.domain.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // 첫 페이지 조회 (cursor 없이 최신순)
    @Query("SELECT p FROM Product p WHERE p.productType = :category ORDER BY p.id DESC")
    Slice<Product> findFirstPage(@Param("category") String category, Pageable pageable);

    // cursor 기반 조회
    @Query("SELECT p FROM Product p WHERE p.productType = :category AND p.id < :cursor ORDER BY p.id DESC")
    Slice<Product> findByCursor(@Param("category") String category, @Param("cursor") Long cursor, Pageable pageable);
}
