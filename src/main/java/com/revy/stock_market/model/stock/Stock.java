package com.revy.stock_market.model.stock;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 주식 마스터 데이터
 */
@Entity
@Table(name="stock")
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
public class Stock {

    @Id
    @Column(name = "code", length = 6, nullable = false, unique = true)
    private String code;
    @Column(name = "name", length = 50, nullable = false)
    private String name;


    @Builder
    public Stock(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
