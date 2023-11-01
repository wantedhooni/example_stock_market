package com.revy.kakaopaysec.usecase.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 검색시 자동채움용 Dto
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StockDto {

    private String code;
    private String name;

    public StockDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
