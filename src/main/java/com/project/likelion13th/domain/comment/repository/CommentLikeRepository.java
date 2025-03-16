package com.project.likelion13th.domain.comment.repository;

import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    long countByComment(Comment comment);
}
