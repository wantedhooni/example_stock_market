package com.revy.stock_market.usecase;

import com.revy.stock_market.usecase.dto.StockDetailDto;
import com.revy.stock_market.usecase.enums.RealtimeStockTag;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface SearchStockUseCaseV2 {


    /**
     * 요청 주식의 상세정보를 반환한다.
     *
     * @param stockCode
     * @param targetDate
     * @return
     */
    StockDetailDto getStockDetail(String stockCode, LocalDate targetDate);

    /**
     * TAG별 실시간 주식 랭킹 정보를 반환한다.
     *
     * @param realtimeStockTag
     * @param targetDate
     * @param pageable
     * @return
     */
    List<StockDetailDto> getRealtimeRanking(RealtimeStockTag realtimeStockTag, LocalDate targetDate, Pageable pageable);
}
