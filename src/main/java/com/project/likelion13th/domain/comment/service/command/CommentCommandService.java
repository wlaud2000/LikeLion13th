package com.project.likelion13th.domain.comment.service.command;

import com.project.likelion13th.domain.comment.dto.request.CommentReqDTO;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;

public interface CommentCommandService {
    CommentResDTO.CommentDetailDTO createComment(String email, Long reviewId, CommentReqDTO.CreateCommentDTO dto);
    CommentResDTO.CommentDetailDTO updateComment(String email, Long commentId, CommentReqDTO.UpdateCommentDTO dto);
    void deleteComment(String email, Long commentId);
    CommentResDTO.LikeResultDTO commentLike(String email, Long commentId);
}
