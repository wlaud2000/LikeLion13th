package com.project.likelion13th.domain.comment.converter;

import com.project.likelion13th.domain.comment.dto.request.CommentReqDTO;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;
import com.project.likelion13th.domain.comment.entity.Comment;
import com.project.likelion13th.domain.member.entity.Member;
import com.project.likelion13th.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentConverter {

    public static Comment toComment(Member member, Review review, CommentReqDTO.CreateCommentDTO dto){
        return Comment.builder()
                .content(dto.getContent())
                .review(review)
                .member(member)
                .build();
    }

    public static CommentResDTO.CommentDetailDTO toCommentDetailDTO(Comment comment, Long likeCount){
        return CommentResDTO.CommentDetailDTO.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .author(comment.getMember().getNickname())
                .createdAt(comment.getCreatedAt().toString())
                .likeCount(likeCount)
                .build();
    }


    public static CommentResDTO.CommentListDTO toCommentListDTO(Slice<Object[]> comments){


        List<CommentResDTO.CommentDetailDTO> commentDetailDTOS = comments.getContent().stream()
                .map(data -> {
                    Comment comment = (Comment) data[0];
                    Long likeCount = (Long) data[1];
                    return toCommentDetailDTO(comment, likeCount);
                }).toList();

        return CommentResDTO.CommentListDTO.builder()
                .comments(commentDetailDTOS)
                .hasNext(comments.hasNext())
                .nextCursor(commentDetailDTOS.isEmpty() ?
                        0L :
                        commentDetailDTOS.get(comments.getContent().size() - 1).getCommentId())
                .build();
    }

    public static CommentResDTO.LikeResultDTO toLikeResultDTO(Long commentId, Boolean isLiked, Long likeCount) {
        return CommentResDTO.LikeResultDTO.builder()
                .commentId(commentId)
                .isLiked(isLiked)
                .likeCount(likeCount)
                .build();
    }

}
