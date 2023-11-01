package com.revy.kakaopaysec.model.stock_daily;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStockDaily is a Querydsl query type for StockDaily
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockDaily extends EntityPathBase<StockDaily> {

    private static final long serialVersionUID = -811439949L;

    public static final QStockDaily stockDaily = new QStockDaily("stockDaily");

    public final StringPath code = createString("code");

    public final NumberPath<java.math.BigDecimal> currentPrice = createNumber("currentPrice", java.math.BigDecimal.class);

    public final NumberPath<Double> fluctuationRate = createNumber("fluctuationRate", Double.class);

    public final NumberPath<java.math.BigDecimal> highestPrice = createNumber("highestPrice", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<java.math.BigDecimal> lowestPrice = createNumber("lowestPrice", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> openingPrice = createNumber("openingPrice", java.math.BigDecimal.class);

    public final DatePath<java.time.LocalDate> targetDate = createDate("targetDate", java.time.LocalDate.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final NumberPath<Integer> volume = createNumber("volume", Integer.class);

    public QStockDaily(String variable) {
        super(StockDaily.class, forVariable(variable));
    }

    public QStockDaily(Path<? extends StockDaily> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStockDaily(PathMetadata metadata) {
        super(StockDaily.class, metadata);
    }

}

