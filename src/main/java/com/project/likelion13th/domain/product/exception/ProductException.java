package com.project.likelion13th.domain.product.exception;

import com.project.likelion13th.global.apiPayload.exception.CustomException;
import lombok.Getter;

@Getter
public class ProductException extends CustomException {

    public ProductException(ProductErrorCode errorCode) {
        super(errorCode);
    }
}
