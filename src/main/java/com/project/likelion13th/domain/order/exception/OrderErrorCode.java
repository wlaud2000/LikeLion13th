package com.project.likelion13th.domain.order.exception;

import com.project.likelion13th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements BaseErrorCode {

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "ORDER404", "주문을 찾을 수 없습니다."),
    ORDER_UNAUTHORIZED(HttpStatus.FORBIDDEN, "ORDER403", "주문을 수정/취소할 권한이 없습니다."),
    ORDER_ALREADY_DELIVERED(HttpStatus.BAD_REQUEST, "ORDER400", "이미 배송 완료된 주문은 취소할 수 없습니다."),
    ORDER_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "ORDER400_1", "이미 취소된 주문입니다."),
    ORDER_EMPTY_PRODUCT(HttpStatus.BAD_REQUEST, "ORDER400_2", "주문에 상품이 없습니다."),
    ORDER_INVALID_STATUS(HttpStatus.BAD_REQUEST, "ORDER400_3", "유효하지 않은 주문 상태입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
