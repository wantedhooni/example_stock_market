package com.revy.kakaopaysec.endpoint.res;

import com.revy.kakaopaysec.common.enums.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorRes {
    private int code;
    private String message;

    public ErrorRes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorRes(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
