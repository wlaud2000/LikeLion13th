package com.project.likelion13th.domain.comment.repository;

import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.comment.entity.CommentLike;
import com.project.likelion13th.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    @Query("SELECT COUNT(cl) FROM CommentLike cl WHERE cl.comment.id = :commentId")
    Long countByCommentId(@Param("commentId") Long commentId);

    // 특정 사용자의 특정 댓글에 대한 좋아요 조회
    Optional<CommentLike> findByMemberAndComment(Member member, Comment comment);

    // 특정 댓글에 대한 모든 좋아요 조회
    List<CommentLike> findAllByComment(Comment comment);
}
