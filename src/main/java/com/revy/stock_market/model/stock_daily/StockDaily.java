package com.revy.stock_market.model.stock_daily;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 일별 주식 현황
 */
@Entity
@Table(name = "stock_daily",
        uniqueConstraints =
                {@UniqueConstraint(name = "UK_TARGET_DATE_CODE", columnNames = {"target_date", "code"})
                })
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@ToString
public class StockDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    // TODO: REVY code + targetDate 는 유니크해야 한다.
    @Column(name = "code", length = 6, nullable = false)
    private String code;

    @Column(name = "target_date", nullable = false)
    private LocalDate targetDate;

    @Column(name = "opening_price", nullable = false)
    private BigDecimal openingPrice; // 시가
    @Column(name = "current_price", nullable = false)
    private BigDecimal currentPrice; // 주가

    @Column(name = "highest_price", nullable = false)
    private BigDecimal highestPrice; // 고가
    @Column(name = "lowest_price", nullable = false)
    private BigDecimal lowestPrice; // 저가

    @Column(name = "fluctuation_rate", nullable = false)
    private double fluctuationRate = 0; //변동률

    @Column(name = "volume", nullable = false)
    private int volume = 0; // 거래량(동수 거래)

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0; // 조회수


    @Builder
    public StockDaily(String code, LocalDate targetDate, BigDecimal openingPrice, BigDecimal currentPrice, BigDecimal highestPrice, BigDecimal lowestPrice, double fluctuationRate, int volume, int viewCount) {
        this.code = code;
        this.targetDate = targetDate;
        this.openingPrice = openingPrice;
        this.currentPrice = currentPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.fluctuationRate = fluctuationRate;
        this.volume = volume;
        this.viewCount = viewCount;
    }
}
