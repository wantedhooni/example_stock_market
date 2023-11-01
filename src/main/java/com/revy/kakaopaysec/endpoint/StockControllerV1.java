package com.revy.kakaopaysec.endpoint;

import com.revy.kakaopaysec.common.constants.CommonConstant;
import com.revy.kakaopaysec.usecase.SearchStockUseCaseV1;
import com.revy.kakaopaysec.usecase.dto.StockDetailDto;
import com.revy.kakaopaysec.usecase.dto.StockDto;
import com.revy.kakaopaysec.usecase.enums.RealtimeStockTag;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(CommonConstant.API_V1 + "/stock")
@Tag(name = "JPA ENTITY -> DTO 변경 기준 API")
public class StockControllerV1 {

    private final SearchStockUseCaseV1 searchStockUseCase;

    /**
     * 주식 목록을 검색한다.
     * @param searchKeyword
     * @param pageSize
     * @return
     */
    @GetMapping("/search")
    public List<StockDto> searchStock(@RequestParam(name = "searchKeyword") String searchKeyword,
                                      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        log.info("[searchStock] searchKeyword: {}", searchKeyword);
        return searchStockUseCase.searchStock(searchKeyword);
    }

    /**
     * 단일 주식 정보를 반환한다.
     *
     * @param stockCode
     */
    @GetMapping("/detail/{stockCode}")
    public StockDetailDto getStockDetail(@PathVariable(name = "stockCode") String stockCode) {
        log.info("[getStockDetail] code: {}", stockCode);
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
        log.info("[getRealtimeRanking] tag: {}", tag);
        return searchStockUseCase.getRealtimeRanking(tag, LocalDate.now(), Pageable.ofSize(pageSize).withPage(page - 1));
    }


}
