package com.project.likelion13th.domain.order.exception;

import com.project.likelion13th.global.apiPayload.exception.CustomException;

public class OrderException extends CustomException {

    public OrderException(OrderErrorCode errorCode) {
        super(errorCode);
    }
}
