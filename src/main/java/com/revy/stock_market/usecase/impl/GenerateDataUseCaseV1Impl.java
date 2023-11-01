package com.revy.stock_market.usecase.impl;

import com.revy.stock_market.model.stock.Stock;
import com.revy.stock_market.model.stock.StockService;
import com.revy.stock_market.model.stock_daily.StockDaily;
import com.revy.stock_market.model.stock_daily.StockDailyService;
import com.revy.stock_market.usecase.GenerateDataUseCaseV1;
import com.revy.stock_market.usecase.dto.StockCsvRecordDto;
import com.revy.stock_market.utils.BigDecimalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class GenerateDataUseCaseV1Impl implements GenerateDataUseCaseV1 {

    private final StockService stockService;
    private final StockDailyService stockDailyService;

    private final Random RANDOM = new Random();

    @Override
    public void initData(List<StockCsvRecordDto> initDataList) {
        Assert.notNull(initDataList, "initDataList must not be null.");

        if (initDataList.isEmpty()) {
            return;
        }

        List<Stock> stockList = initDataList.stream().map(data -> {
            Stock stock = Stock.builder()
                    .code(data.getCode())
                    .name(data.getName())
                    .build();
            return stock;
        }).toList();

        stockService.saveAll(stockList);
    }

    @Override
    public void initDailyData(List<StockCsvRecordDto> initDataList) {
        Assert.notNull(initDataList, "initDataList must not be null.");

        if (initDataList.isEmpty()) {
            return;
        }

        LocalDate targetDate = LocalDate.now();

        List<StockDaily> stockDailyList = initDataList.stream()
                .filter(data -> stockDailyService.findOne(data.getName(), targetDate) == null)
                .map(data -> StockDaily.builder()
                        .code(data.getCode())
                        .targetDate(targetDate)
                        .openingPrice(data.getPrice())
                        .currentPrice(data.getPrice())
                        .highestPrice(data.getPrice())
                        .lowestPrice(data.getPrice())
                        .fluctuationRate(0)
                        .volume(0)
                        .viewCount(0)
                        .build()).toList();

        stockDailyService.saveAll(stockDailyList);
    }

    @Async
    @Override
    public void modifyRandomStockData() {
        this.modifyRandomStockData(LocalDate.now());
    }

    @Async
    @Override
    public void modifyRandomStockData(LocalDate targetDate) {
        this.modifyRandomStockData(stockDailyService.find(targetDate));
    }

    @Async
    @Override
    public void modifyRandomStockData(String code, LocalDate targetDate) {
        this.modifyRandomStockData(Collections.singletonList(stockDailyService.findOne(code, targetDate)));
    }

    private void modifyRandomStockData(List<StockDaily> stockDailyList) {
        Assert.notNull(stockDailyList, "stockDailyList must be not null.");
        stockDailyList.forEach(this::modifyRandomStockData);
    }

    /**
     * stockDaily 객체에 데이터를 랜덤으로 변경한다.
     * 1. 조회수
     * 2. 거래량
     * 3. 현재가격, 변동량
     * @param stockDaily
     */
    private void modifyRandomStockData(StockDaily stockDaily) {
        Assert.notNull(stockDaily, "stockDaily must be not null.");
        stockDaily.setViewCount(RANDOM.nextInt(1000));
        stockDaily.setVolume(RANDOM.nextInt(100000));

        BigDecimal currentPrice = BigDecimal.ZERO;
        // 0원 이상이면 멈춘다.
        while (true) {
            int price = RANDOM.nextInt(stockDaily.getOpeningPrice().intValue() - 10000, stockDaily.getOpeningPrice().intValue() + 10000);
            currentPrice = new BigDecimal(price);
            if (BigDecimalUtils.isGreaterThan(currentPrice, BigDecimal.ZERO)) {
                break;
            }
        }
        stockDaily = stockDailyService.modifyCurrentPrice(stockDaily, currentPrice);
        stockDailyService.save(stockDaily);
    }

}
