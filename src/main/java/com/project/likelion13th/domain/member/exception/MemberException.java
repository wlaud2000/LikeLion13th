package com.project.likelion13th.domain.member.exception;

import com.project.likelion13th.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class MemberException extends CustomException {

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
