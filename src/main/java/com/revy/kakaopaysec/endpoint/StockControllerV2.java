package com.revy.kakaopaysec.endpoint;

import com.revy.kakaopaysec.common.constants.CommonConstant;
import com.revy.kakaopaysec.usecase.SearchStockUseCaseV2;
import com.revy.kakaopaysec.usecase.dto.StockDetailDto;
import com.revy.kakaopaysec.usecase.enums.RealtimeStockTag;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(CommonConstant.API_V2 + "/stock")
@Tag(name = "QueryDsl 버전 API")
public class StockControllerV2 {

    private final SearchStockUseCaseV2 searchStockUseCase;


    /**
     * 단일 주식 정보를 반환한다.
     *
     * @param stockCode
     */
    @GetMapping("/detail/{stockCode}")
    public StockDetailDto getStockDetail(@PathVariable(name = "stockCode") String stockCode) {
        log.info("[V2-getStockDetail] code: {}", stockCode);
        return searchStockUseCase.getStockDetail(stockCode, LocalDate.now());
    }

    /**
     * 실시간 주식 랭킹 정보를 반환한다.
     *
     * @param tag
     * @param pageSize
     * @param page
     */
    @GetMapping("/realtime-ranking")
    public List<StockDetailDto> getRealtimeRanking(@RequestParam(name = "tag", defaultValue = "POPULARITY") RealtimeStockTag tag,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                   @RequestParam(name = "page", defaultValue = "1") int page) {
        log.info("[V2-getRealtimeRanking] tag: {}", tag);
        return searchStockUseCase.getRealtimeRanking(tag, LocalDate.now(), Pageable.ofSize(pageSize).withPage(page - 1));
    }


}
