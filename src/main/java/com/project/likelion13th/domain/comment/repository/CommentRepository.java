package com.project.likelion13th.domain.comment.repository;

import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.product.entity.Product;
import com.project.likelion13th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 첫 페이지 조회
    @Query("SELECT c, (SELECT COUNT(cl) FROM CommentLike cl WHERE cl.comment = c) AS likeCount " +
            "FROM Comment c WHERE c.review = :review ORDER BY c.id DESC")
    Slice<Object[]> findFirstPageWithLikeCount(@Param("review") Review review, Pageable pageable);

    // 커서 기반 페이지 조회
    @Query("SELECT c, (SELECT COUNT(cl) FROM CommentLike cl WHERE cl.comment = c) AS likeCount " +
            "FROM Comment c WHERE c.review = :review AND c.id < :cursor ORDER BY c.id DESC")
    Slice<Object[]> findByCursorWithLikeCount(@Param("review") Review review, @Param("cursor") Long cursor, Pageable pageable);
}