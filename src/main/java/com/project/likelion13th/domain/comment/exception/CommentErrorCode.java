package com.project.likelion13th.domain.comment.exception;

import com.project.likelion13th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements BaseErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT404", "댓글을 찾을 수 없습니다."),
    COMMENT_UNAUTHORIZED(HttpStatus.FORBIDDEN, "COMMENT403", "댓글을 수정/삭제할 권한이 없습니다."),
    COMMENT_ALREADY_LIKED(HttpStatus.BAD_REQUEST, "COMMENT400", "이미 좋아요한 댓글입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

