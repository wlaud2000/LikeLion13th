package com.project.likelion13th.domain.comment.exception;

import com.project.likelion13th.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class CommentException extends CustomException {

    public CommentException(CommentErrorCode errorCode) {
        super(errorCode);
    }
}
