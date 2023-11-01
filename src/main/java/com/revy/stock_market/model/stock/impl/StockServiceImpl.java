package com.revy.stock_market.model.stock.impl;

import com.revy.stock_market.model.stock.Stock;
import com.revy.stock_market.model.stock.StockRepo;
import com.revy.stock_market.model.stock.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepo stockRepo;

    @Override
    public Optional<Stock> findOne(String code) {
        return stockRepo.findById(code);
    }

    @Override
    public List<Stock> saveAll(List<Stock> stockList) {
        return stockRepo.saveAll(stockList);
    }

    @Override
    public List<Stock> search(String searchKeyword) {
        return stockRepo.findStocksByCodeAndNameLike("%" + searchKeyword + "%");
    }
}
