package com.project.likelion13th.domain.review.repository;

import com.project.likelion13th.domain.order.entity.Order;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 첫 페이지 조회 (cursor 없이 최신순)
    @Query("SELECT r FROM Review r WHERE r.product = :product ORDER BY r.id DESC")
    Slice<Review> findFirstPage(@Param("product") Product productId, Pageable pageable);

    // cursor 기반 조회
    @Query("SELECT r FROM Review r WHERE r.product = :product AND r.id < :cursor ORDER BY r.id DESC")
    Slice<Review> findByCursor(@Param("product") Product productId, @Param("cursor") Long cursor, Pageable pageable);

    // 첫 페이지 조회
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId " +
            "ORDER BY r.id DESC")
    Slice<Review> findFirstPageByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 커서 기반 페이지 조회
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId AND r.id < :cursor " +
            "ORDER BY r.id DESC")
    Slice<Review> findByMemberIdAndCursor(@Param("memberId") Long memberId, @Param("cursor") Long cursor, Pageable pageable);

}
