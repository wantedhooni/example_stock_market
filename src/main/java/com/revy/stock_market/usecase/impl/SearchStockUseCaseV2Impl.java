package com.revy.stock_market.usecase.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revy.stock_market.common.enums.ErrorCode;
import com.revy.stock_market.model.stock.QStock;
import com.revy.stock_market.model.stock_daily.QStockDaily;
import com.revy.stock_market.model.stock_daily.StockDailyService;
import com.revy.stock_market.usecase.SearchStockUseCaseV2;
import com.revy.stock_market.usecase.dto.StockDetailDto;
import com.revy.stock_market.usecase.enums.RealtimeStockTag;
import com.revy.stock_market.usecase.exception.UseCaseException;
import jakarta.annotation.Nullable;
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
public class SearchStockUseCaseV2Impl implements SearchStockUseCaseV2 {

    private final StockDailyService stockDailyService;

    private final JPAQueryFactory jpaQueryFactory;

    private final QStock STOCK = QStock.stock;
    private final QStockDaily STOCK_DAILY = QStockDaily.stockDaily;

    @Override
    public StockDetailDto getStockDetail(String stockCode, LocalDate targetDate) {
        Assert.hasText(stockCode, "stockCode must be not empty.");
        Assert.notNull(targetDate, "targetDate must be not empty.");
        StockDetailDto stockDaily = this.findOne(stockCode, targetDate);

        if (stockDaily == null) {
            throw new UseCaseException("데이터가 존재하지 않습니다.", ErrorCode.NOT_FOUND_ENTITY);
        }

        // TODO: REVY 해당로직 Async로 넘겨 버리면 ReadOnly로 수행 가능하다.
        // 조회 카운트를 1 증가 시킨다.
        stockDailyService.incrementViewCount(stockCode, targetDate);

        return stockDaily;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockDetailDto> getRealtimeRanking(RealtimeStockTag realtimeStockTag, LocalDate targetDate, Pageable pageable) {
        Assert.notNull(realtimeStockTag, "realtimeStockTag must be not empty.");
        Assert.notNull(targetDate, "targetDate must be not empty.");
        Assert.notNull(pageable, "pageable must be not empty.");

        return switch (realtimeStockTag) {
            case POPULARITY -> this.searchPopularty(targetDate, pageable);
            case UPWARD -> this.searchUpward(targetDate, pageable);
            case DOWNWARD -> this.searchDownward(targetDate, pageable);
            case TRADING_VOLUME -> this.searchTradingVolume(targetDate, pageable);
            default -> {
                throw new UseCaseException("지원하지 않는 실시간 랭킹 타입니다.", ErrorCode.BAD_REQUEST);
            }
        };

    }

    private List<StockDetailDto> searchTradingVolume(LocalDate targetDate, Pageable pageable) {
        return getDefaultQueryForStockDetail(targetDate, pageable)
                .orderBy(STOCK_DAILY.volume.desc())
                .fetch();
    }

    private List<StockDetailDto> searchDownward(LocalDate targetDate, Pageable pageable) {
        return getDefaultQueryForStockDetail(targetDate, pageable)
                .orderBy(STOCK_DAILY.fluctuationRate.asc())
                .fetch();
    }

    private List<StockDetailDto> searchUpward(LocalDate targetDate, Pageable pageable) {
        return getDefaultQueryForStockDetail(targetDate, pageable)
                .orderBy(STOCK_DAILY.fluctuationRate.desc())
                .fetch();
    }

    private List<StockDetailDto> searchPopularty(LocalDate targetDate, Pageable pageable) {
        return getDefaultQueryForStockDetail(targetDate, pageable)
                .orderBy(STOCK_DAILY.viewCount.desc())
                .fetch();
    }

    private StockDetailDto findOne(String stockCode, LocalDate targetDate){
        return getDefaultQueryForStockDetail(targetDate).where(STOCK_DAILY.code.eq(stockCode)).fetchFirst();
    }

    private JPAQuery<StockDetailDto> getDefaultQueryForStockDetail(LocalDate targetDate){
        return getDefaultQueryForStockDetail(targetDate, null);
    }

    /**
     *
     * @param targetDate 조회 기준일자
     * @param pageable 페이징 정보
     */
    private JPAQuery<StockDetailDto> getDefaultQueryForStockDetail(LocalDate targetDate, @Nullable Pageable pageable){
        JPAQuery<StockDetailDto> baseQuery =  jpaQueryFactory
                .select(Projections.bean(StockDetailDto.class,
                        STOCK_DAILY.code.as("code"),
                        STOCK.name.as("name"),
                        STOCK_DAILY.targetDate.as("targetDate"),
                        STOCK_DAILY.openingPrice.as("openingPrice"),
                        STOCK_DAILY.currentPrice.as("currentPrice"),
                        STOCK_DAILY.highestPrice.as("highestPrice"),
                        STOCK_DAILY.lowestPrice.as("lowestPrice"),
                        STOCK_DAILY.fluctuationRate.as("fluctuationRate"),
                        STOCK_DAILY.volume.as("volume"),
                        STOCK_DAILY.viewCount.as("viewCount")
                ))
                .from(STOCK_DAILY)
                .where(STOCK_DAILY.targetDate.eq(targetDate))
                .leftJoin(STOCK).on(STOCK_DAILY.code.eq(STOCK.code));

                if(pageable != null){
                    baseQuery.offset(pageable.getPageNumber()).limit(pageable.getPageSize());
                }
        return baseQuery;
    }
}
