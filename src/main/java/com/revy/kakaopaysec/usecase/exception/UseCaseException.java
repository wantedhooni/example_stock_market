package com.revy.kakaopaysec.usecase.exception;

import com.revy.kakaopaysec.common.enums.ErrorCode;
import lombok.Getter;

/**
 * 비지니스 로직에서 발생하는 Excpeion
 * usecase에서 발생하는 Exception 정의
 *
 */
public class UseCaseException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;

    public UseCaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
