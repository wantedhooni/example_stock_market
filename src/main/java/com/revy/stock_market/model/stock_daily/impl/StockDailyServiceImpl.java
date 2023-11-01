package com.revy.stock_market.model.stock_daily.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revy.stock_market.model.stock_daily.QStockDaily;
import com.revy.stock_market.model.stock_daily.StockDaily;
import com.revy.stock_market.model.stock_daily.StockDailyRepo;
import com.revy.stock_market.model.stock_daily.StockDailyService;
import com.revy.stock_market.utils.BigDecimalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StockDailyServiceImpl implements StockDailyService {

    private final StockDailyRepo stockDailyRepo;

    private final JPAQueryFactory jpaQueryFactory;

    private final QStockDaily STOCK_DAILY = QStockDaily.stockDaily;
    private final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    @Override
    public void save(StockDaily stockDaily) {
        stockDailyRepo.save(stockDaily);
    }

    @Override
    public void saveAll(List<StockDaily> stockDailyList) {
        stockDailyRepo.saveAll(stockDailyList);
    }

    @Override
    public StockDaily findOne(String code, LocalDate targetDate) {
        return jpaQueryFactory
                .selectFrom(STOCK_DAILY)
                .where(
                        STOCK_DAILY.code.eq(code).and(STOCK_DAILY.targetDate.eq(targetDate))
                )
                .fetchFirst();
    }

    @Override
    public List<StockDaily> find(LocalDate targetDate) {
        return jpaQueryFactory
                .selectFrom(STOCK_DAILY).where(STOCK_DAILY.targetDate.eq(targetDate))
                .fetch();
    }


    @Override
    public void incrementViewCount(String code, LocalDate targetDate) {
        stockDailyRepo.incrementViewCount(code, targetDate);
    }

    @Override
    public List<StockDaily> searchPopularty(LocalDate targetDate, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(STOCK_DAILY)
                .where(STOCK_DAILY.targetDate.eq(targetDate))
                .orderBy(STOCK_DAILY.viewCount.desc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<StockDaily> searchUpward(LocalDate targetDate, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(STOCK_DAILY)
                .where(STOCK_DAILY.targetDate.eq(targetDate))
                .orderBy(STOCK_DAILY.fluctuationRate.desc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<StockDaily> searchDownward(LocalDate targetDate, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(STOCK_DAILY)
                .where(STOCK_DAILY.targetDate.eq(targetDate))
                .orderBy(STOCK_DAILY.fluctuationRate.asc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<StockDaily> searchTradingVolume(LocalDate targetDate, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(STOCK_DAILY)
                .where(STOCK_DAILY.targetDate.eq(targetDate))
                .orderBy(STOCK_DAILY.volume.desc())
                .offset(pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public StockDaily modifyCurrentPrice(StockDaily stockDaily, BigDecimal currentPrice) {
        Assert.notNull(stockDaily, "stockDaily must be not null.");
        Assert.notNull(currentPrice, "currentPrice must be not null.");


        double fluctuationRate = currentPrice
                .subtract(stockDaily.getOpeningPrice())
                .divide(stockDaily.getOpeningPrice(), 5, RoundingMode.DOWN)
                .multiply(ONE_HUNDRED).doubleValue(); // 변동률

        // 현재가격, 변동률 셋팅
        stockDaily.setCurrentPrice(currentPrice);
        stockDaily.setFluctuationRate(fluctuationRate);

        // 고가 셋팅
        if(BigDecimalUtils.isGreaterThan(currentPrice, stockDaily.getHighestPrice())){
            stockDaily.setHighestPrice(currentPrice);
        }

        // 저가 셋팅
        if(BigDecimalUtils.isLessThan(currentPrice, stockDaily.getLowestPrice())){
            stockDaily.setLowestPrice(currentPrice);
        }

        return stockDaily;
    }
}
