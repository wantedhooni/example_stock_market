package com.revy.kakaopaysec.usecase;

import com.revy.kakaopaysec.usecase.enums.RealtimeStockTag;
import com.revy.kakaopaysec.usecase.exception.UseCaseException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchStockUseCaseV1Test {

    @Autowired
    private SearchStockUseCaseV1 searchStockUseCaseV1;

    @DisplayName("주식 정보 검색 테스트")
    @Order(1)
    @Test
    void searchStockTest(){

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> searchStockUseCaseV1.searchStock(""));
        Assertions.assertFalse(searchStockUseCaseV1.searchStock("삼성전자").isEmpty());

    }

    @DisplayName("주식 상세정보 조회 테스트")
    @Order(2)
    @Test
    void getStockDetail(){
        LocalDate targetDate = LocalDate.now();
        // param Validation Error
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> searchStockUseCaseV1.getStockDetail("", targetDate));
        // 존재하지 않는 도메인 에러
        Assertions.assertThrows(UseCaseException.class,
                () -> searchStockUseCaseV1.getStockDetail("000000", targetDate));
        // 삼성전자 조회
        Assertions.assertNotNull(searchStockUseCaseV1.getStockDetail("005930", targetDate));
    }


    @DisplayName("실시간 랭킹 정보 조회 테스트")
    @Order(3)
    @Test
    void getRealtimeRanking(){
        LocalDate targetDate = LocalDate.now();
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        // param Validation Test
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> searchStockUseCaseV1.getRealtimeRanking(null, targetDate, pageable));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> searchStockUseCaseV1.getRealtimeRanking(RealtimeStockTag.POPULARITY, null, pageable));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> searchStockUseCaseV1.getRealtimeRanking(RealtimeStockTag.POPULARITY, targetDate, null));

        // 금일 조회수 많은 종목 TEST
        Assertions.assertFalse(searchStockUseCaseV1.getRealtimeRanking(RealtimeStockTag.POPULARITY, targetDate, pageable).isEmpty());
        // 금일 가격 하락 종목 TEST
        Assertions.assertFalse(searchStockUseCaseV1.getRealtimeRanking(RealtimeStockTag.DOWNWARD, targetDate, pageable).isEmpty());
        // 금일 가격 상승 종목 TEST
        Assertions.assertFalse(searchStockUseCaseV1.getRealtimeRanking(RealtimeStockTag.UPWARD, targetDate, pageable).isEmpty());
        // 거래량 많은 종목 조회 TEST
        Assertions.assertFalse(searchStockUseCaseV1.getRealtimeRanking(RealtimeStockTag.TRADING_VOLUME, targetDate, pageable).isEmpty());
    }

}