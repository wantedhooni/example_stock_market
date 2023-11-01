package com.revy.stock_market.common.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    BAD_REQUEST(400,  "잘못된 요청정보 입니다."),
    NOT_FOUND_ENTITY(404,  "해당 데이터는 존재하지 않습니다."),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;

    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
