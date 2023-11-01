package com.revy.kakaopaysec.model.stock_daily;

import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StockDailyService {
    void save(StockDaily stockDaily);

    void saveAll(List<StockDaily> stockDailyList);

    StockDaily findOne(String code, LocalDate localDate);

    List<StockDaily> find(LocalDate targetDate);

    void incrementViewCount(String stockCode, LocalDate localDate);

    List<StockDaily> searchPopularty(LocalDate targetDate, Pageable pageable);
    List<StockDaily> searchUpward(LocalDate targetDate, Pageable pageable);

    List<StockDaily> searchDownward(LocalDate targetDate, Pageable pageable);

    List<StockDaily> searchTradingVolume(LocalDate targetDate, Pageable pageable);

    StockDaily modifyCurrentPrice(StockDaily stockDaily, BigDecimal currentPrice);
}
