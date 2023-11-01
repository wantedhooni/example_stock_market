package com.revy.kakaopaysec.endpoint;

import com.revy.kakaopaysec.common.constants.CommonConstant;
import com.revy.kakaopaysec.usecase.GenerateDataUseCaseV1;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * data 랜덤 변경 EndPoint
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(CommonConstant.API_V1 + "/generate-data")
@Tag(name = "데이터 랜점 변경 API")
public class GenerateDataControllerV1 {

    private final GenerateDataUseCaseV1 generateDataUseCase;

    /**
     * 현재기준의 요청 주식의 데이터를 랜덤으로 변경한다.
     * @param stockCode
     */
    @GetMapping("/random/{stockCode}")
    public void generateRandomDataOne(@PathVariable(name = "stockCode") String stockCode) {
        Assert.hasText(stockCode, "stockCode must not be empty.");
        generateDataUseCase.modifyRandomStockData(stockCode, LocalDate.now());

    }

    /**
     * 현재기준의 모든 주식 정보의 데이터를 랜덤으로 변경한다.
     */
    @GetMapping("/random/all")
    public void generateRandomDataAll() {
        generateDataUseCase.modifyRandomStockData(LocalDate.now());
    }


}
