package com.project.likelion13th.domain.review.exception;

import com.project.likelion13th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements BaseErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW404", "리뷰를 찾을 수 없습니다."),
    REVIEW_UNAUTHORIZED(HttpStatus.FORBIDDEN, "REVIEW403", "리뷰를 수정/삭제할 권한이 없습니다."),
    PRODUCT_NOT_ORDERED(HttpStatus.BAD_REQUEST, "REVIEW400_1", "구매하지 않은 상품에는 리뷰를 작성할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
