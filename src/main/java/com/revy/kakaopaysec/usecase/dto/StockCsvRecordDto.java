package com.revy.kakaopaysec.usecase.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class StockCsvRecordDto {

    private String code;
    private String name;
    private BigDecimal price;

    @Builder
    public StockCsvRecordDto(String code, String name, BigDecimal price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
}
