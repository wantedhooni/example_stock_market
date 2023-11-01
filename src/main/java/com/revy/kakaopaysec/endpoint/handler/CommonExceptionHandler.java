package com.revy.kakaopaysec.endpoint.handler;

import com.revy.kakaopaysec.common.enums.ErrorCode;
import com.revy.kakaopaysec.endpoint.res.ErrorRes;
import com.revy.kakaopaysec.usecase.exception.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalAccessException.class)
    public ErrorRes handleIllegalAccessException(IllegalAccessException e) {
        log.warn("{}", e);
        return new ErrorRes(ErrorCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorRes handleException(Exception e) {
        log.warn("{}", e);
        return new ErrorRes(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UseCaseException.class)
    public ErrorRes handleUseCaseException(UseCaseException e) {
        log.warn("{}", e);
        return new ErrorRes(e.getErrorCode());
    }

}
