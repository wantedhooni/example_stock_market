package com.revy.stock_market.usecase.enums;

import lombok.Getter;

/**
 * 실시간 주식 검색 TAG
 */
@Getter
public enum RealtimeStockTag {
    POPULARITY("금일 사람들이 많이 본 주식"),
    UPWARD("금일 가격이 많이 오른 주식"),
    DOWNWARD("금일 가격이 많이 내린 주식"),
    TRADING_VOLUME("금일 거래량 많은 주식");

    private final String description;

    RealtimeStockTag(String description) {
        this.description = description;
    }
}
