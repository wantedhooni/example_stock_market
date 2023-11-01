package com.revy.kakaopaysec.model.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StockRepo extends JpaRepository<Stock, String> {

    @Query("SELECT stock FROM Stock stock WHERE stock.code LIKE :keyword OR stock.name LIKE :keyword ORDER BY stock.name ASC")
    List<Stock> findStocksByCodeAndNameLike(@Param("keyword") String keyword);
}
