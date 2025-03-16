package com.project.likelion13th.domain.comment.service.query;

import com.project.likelion13th.domain.comment.dto.response.CommentResDTO;

public interface CommentQueryService {
    CommentResDTO.CommentListDTO getCommentList(Long reviewId, Long cursor, int size);

}
