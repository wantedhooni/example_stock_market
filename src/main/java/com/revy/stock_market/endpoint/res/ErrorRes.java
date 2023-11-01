package com.revy.stock_market.endpoint.res;

import com.revy.stock_market.common.enums.ErrorCode;
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
