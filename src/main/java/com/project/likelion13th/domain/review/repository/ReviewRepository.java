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

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 첫 페이지 조회 (삭제되지 않은 리뷰만)
    @Query("SELECT r FROM Review r WHERE r.product = :product AND r.deletedAt IS NULL ORDER BY r.id DESC")
    Slice<Review> findFirstPage(@Param("product") Product product, Pageable pageable);

    // cursor 기반 조회 (삭제되지 않은 리뷰만)
    @Query("SELECT r FROM Review r WHERE r.product = :product AND r.id < :cursor AND r.deletedAt IS NULL ORDER BY r.id DESC")
    Slice<Review> findByCursor(@Param("product") Product product, @Param("cursor") Long cursor, Pageable pageable);

    // 내 리뷰 첫 페이지 조회 (삭제되지 않은 리뷰만)
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId AND r.deletedAt IS NULL " +
            "ORDER BY r.id DESC")
    Slice<Review> findFirstPageByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    // 내 리뷰 커서 기반 페이지 조회 (삭제되지 않은 리뷰만)
    @Query("SELECT r FROM Review r " +
            "WHERE r.member.id = :memberId AND r.id < :cursor AND r.deletedAt IS NULL " +
            "ORDER BY r.id DESC")
    Slice<Review> findByMemberIdAndCursor(@Param("memberId") Long memberId, @Param("cursor") Long cursor, Pageable pageable);

    // 삭제되지 않은 리뷰 ID로 조회
    @Query("SELECT r FROM Review r WHERE r.id = :id AND r.deletedAt IS NULL")
    Optional<Review> findByIdAndNotDeleted(@Param("id") Long id);
}
