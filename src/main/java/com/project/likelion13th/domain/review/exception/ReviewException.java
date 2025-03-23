package com.project.likelion13th.domain.review.exception;

import com.project.likelion13th.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class ReviewException extends CustomException {

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode);
    }
}
