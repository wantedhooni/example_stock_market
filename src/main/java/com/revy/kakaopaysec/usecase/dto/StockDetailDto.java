package com.revy.kakaopaysec.usecase.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class StockDetailDto {

    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonProperty("targetDate")
    private LocalDate targetDate;

    @JsonProperty("openingPrice")
    private BigDecimal openingPrice; // 시가
    @JsonProperty("currentPrice")
    private BigDecimal currentPrice; // 주가
    @JsonProperty("highestPrice")
    private BigDecimal highestPrice; // 고가
    @JsonProperty("lowestPrice")
    private BigDecimal lowestPrice; // 저가
    @JsonProperty("fluctuationRate")
    private double fluctuationRate = 0; //변동률
    @JsonProperty("volume")
    private int volume = 0; // 거래량(동수 거래)
    @JsonProperty("viewCount")
    private int viewCount = 0; // 조회수

    @Builder
    public StockDetailDto(String code, String name, LocalDate targetDate, BigDecimal openingPrice, BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowestPrice, double fluctuationRate, int volume, int viewCount) {
        this.code = code;
        this.name = name;
        this.targetDate = targetDate;
        this.openingPrice = openingPrice;
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.fluctuationRate = fluctuationRate;
        this.volume = volume;
        this.viewCount = viewCount;
    }
}
