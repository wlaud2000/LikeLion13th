package com.project.likelion13th.domain.comment.service.command;

import com.project.likelion13th.domain.comment.dto.request.CommentReqDTO;
import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;

public interface CommentCommandService {
    CommentResDTO.CommentDetailDTO createComment(Long reviewId, CommentReqDTO.CreateCommentDTO dto);
    void commentLike(Long commentId);

}
