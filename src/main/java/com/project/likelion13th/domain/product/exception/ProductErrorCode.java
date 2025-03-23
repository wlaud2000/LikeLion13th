package com.project.likelion13th.domain.product.exception;


import com.project.likelion13th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements BaseErrorCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT404_1", "상품을 찾을 수 없습니다."),
    PRODUCT_UNAUTHORIZED(HttpStatus.FORBIDDEN, "PRODUCT403_1", "상품을 수정/삭제할 권한이 없습니다."),
    PRODUCT_IN_USE(HttpStatus.BAD_REQUEST, "PRODUCT400_1", "주문이 있는 상품은 삭제할 수 없습니다."),
    PRODUCT_INVALID_TYPE(HttpStatus.BAD_REQUEST, "PRODUCT400_2", "유효하지 않은 상품 카테고리입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
