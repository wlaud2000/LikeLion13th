package com.project.likelion13th.global.jwt.exception;

import com.project.likelion13th.global.apiPayload.exception.CustomException;

public class AuthException extends CustomException {
    public AuthException(JwtErrorCode code) {
        super(code);
    }
}