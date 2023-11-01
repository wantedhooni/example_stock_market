package com.revy.stock_market.usecase.impl;

import com.revy.stock_market.common.enums.ErrorCode;
import com.revy.stock_market.model.stock.Stock;
import com.revy.stock_market.model.stock.StockService;
import com.revy.stock_market.model.stock_daily.StockDaily;
import com.revy.stock_market.model.stock_daily.StockDailyService;
import com.revy.stock_market.usecase.SearchStockUseCaseV1;
import com.revy.stock_market.usecase.dto.StockDetailDto;
import com.revy.stock_market.usecase.dto.StockDto;
import com.revy.stock_market.usecase.enums.RealtimeStockTag;
import com.revy.stock_market.usecase.exception.UseCaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SearchStockUseCaseV1Impl implements SearchStockUseCaseV1 {

    private final StockService stockService;
    private final StockDailyService stockDailyService;


    @Override
    public List<StockDto> searchStock(String searchKeyword) {
        Assert.hasText(searchKeyword, "searchKeyword must be not empty.");
        return stockService.search(searchKeyword).stream().map(this::convertStockDto).toList();
    }

    @Override
    public StockDetailDto getStockDetail(String stockCode, LocalDate targetDate) {
        Assert.hasText(stockCode, "stockCode must be not empty.");
        Assert.notNull(targetDate, "targetDate must be not empty.");
        StockDaily stockDaily = stockDailyService.findOne(stockCode, targetDate);

        if (stockDaily == null) {
            throw new UseCaseException("데이터가 존재하지 않습니다.", ErrorCode.NOT_FOUND_ENTITY);
        }

        // TODO: REVY 해당로직 Async로 넘겨 버리면 ReadOnly로 수행 가능하다.
        // 조회 카운트를 1 증가 시킨다.
        stockDailyService.incrementViewCount(stockCode, targetDate);
        return convertStockDetailDto(stockDaily);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockDetailDto> getRealtimeRanking(RealtimeStockTag realtimeStockTag, LocalDate targetDate, Pageable pageable) {
        Assert.notNull(realtimeStockTag, "realtimeStockTag must be not empty.");
        Assert.notNull(targetDate, "targetDate must be not empty.");
        Assert.notNull(pageable, "pageable must be not empty.");

        List<StockDaily> stockDailyList =  switch (realtimeStockTag) {
            case POPULARITY -> stockDailyService.searchPopularty(targetDate, pageable);
            case UPWARD -> stockDailyService.searchUpward(targetDate, pageable);
            case DOWNWARD -> stockDailyService.searchDownward(targetDate, pageable);
            case TRADING_VOLUME -> stockDailyService.searchTradingVolume(targetDate, pageable);
            default -> {
                throw new UseCaseException("지원하지 않는 실시간 랭킹 타입니다.", ErrorCode.BAD_REQUEST);
            }
        };
        return stockDailyList.stream().map(this::convertStockDetailDto).toList();
    }


    /**
     * Stock 객체를 StockDto로 변환한다.
     * @param stock
     * @return
     */
    private StockDto convertStockDto(Stock stock) {
        Assert.notNull(stock, "Stock must be not empty.");
        return new StockDto(stock.getCode(), stock.getName());
    }

    /**
     * StockDaily 객체를 StockDetailDto로 변환한다.
     * @param stockDaily
     * @return
     */
    private StockDetailDto convertStockDetailDto(StockDaily stockDaily) {
        Assert.notNull(stockDaily, "Stock must be not null.");
        Stock stock = stockService.findOne(stockDaily.getCode()).orElseThrow();
        return StockDetailDto.builder()
                .code(stockDaily.getCode())
                .name(stock.getName())
                .targetDate(stockDaily.getTargetDate())
                .openingPrice(stockDaily.getOpeningPrice())
                .currentPrice(stockDaily.getCurrentPrice())
                .highestPrice(stockDaily.getHighestPrice())
                .lowestPrice(stockDaily.getLowestPrice())
                .fluctuationRate(stockDaily.getFluctuationRate())
                .volume(stockDaily.getVolume())
                .viewCount(stockDaily.getViewCount())
                .build();
    }
}
