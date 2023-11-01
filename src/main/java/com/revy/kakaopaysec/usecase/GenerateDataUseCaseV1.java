package com.revy.kakaopaysec.usecase;


import com.revy.kakaopaysec.usecase.dto.StockCsvRecordDto;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDate;
import java.util.List;

public interface GenerateDataUseCaseV1 {

    /**
     * 주식데이터를 등록힌다.
     *
     * @param initDataList
     */
    void initData(List<StockCsvRecordDto> initDataList);

    /**
     * 금일 기준의 주식 정보를 생성한다.
     *
     * @param initDataList
     */
    void initDailyData(List<StockCsvRecordDto> initDataList);

    /**
     * 요청 일자의 모든 주식 데이터를 랜덤으로 변경한다.(현재 기준)
     */
    @Async
    void modifyRandomStockData();

    /**
     * 요청 일자의 모든 주식 데이터를 랜덤으로 변경한다.
     *
     * @param targetDate
     */
    @Async
    void modifyRandomStockData(LocalDate targetDate);

    /**
     * 요청 일자의 해당 주식 데이터를  랜덤으로 변경한다.
     *
     * @param code
     * @param targetDate
     */
    @Async
    void modifyRandomStockData(String code, LocalDate targetDate);
}
