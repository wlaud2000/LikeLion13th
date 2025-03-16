package com.project.likelion13th.domain.comment.converter;

import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.comment.entity.CommentLike;
import com.project.likelion13th.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentLikeConverter {

    public static CommentLike toEntity(Member member, Comment comment){
        return CommentLike.builder()
                .comment(comment)
                .member(member)
                .build();
    }
}
