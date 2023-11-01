package com.revy.stock_market.model.stock;

import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

public interface StockService {
    @Cacheable(cacheNames = "cache")
    Optional<Stock> findOne(String code);

    List<Stock> saveAll(List<Stock> stockList);

    List<Stock> search(String searchKeyword);
}
