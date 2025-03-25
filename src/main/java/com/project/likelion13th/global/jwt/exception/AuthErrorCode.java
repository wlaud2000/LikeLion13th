package com.project.likelion13th.global.jwt.exception;


import com.project.likelion13th.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN401", "토큰이 유효하지 않습니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "AUTH400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH401", "인증에 실패했습니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH403_1", "권한이 없습니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH404_1", "컨텐츠를 찾지 못했습니다."),
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH500_1", "서버 관리자에게 문의하세요."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}