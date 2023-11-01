package com.revy.kakaopaysec.usecase;

import com.revy.kakaopaysec.usecase.dto.StockDetailDto;
import com.revy.kakaopaysec.usecase.dto.StockDto;
import com.revy.kakaopaysec.usecase.enums.RealtimeStockTag;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface SearchStockUseCaseV1 {

    /**
     * 키워드로 주식을 검색한다.
     *
     * @param searchKeyword (주식코드 OR 주식이름)
     * @return
     */
    @Cacheable(cacheNames = "cache")
    List<StockDto> searchStock(String searchKeyword);

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
