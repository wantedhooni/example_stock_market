package com.revy.stock_market.model.stock_daily;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface StockDailyRepo extends JpaRepository<StockDaily, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE StockDaily stockDaily SET stockDaily.viewCount = stockDaily.viewCount + 1" +
            " WHERE stockDaily.code = :code AND stockDaily.targetDate = :targetDate")
    void incrementViewCount(@Param("code") String code, @Param("targetDate") LocalDate targetDate);
}
