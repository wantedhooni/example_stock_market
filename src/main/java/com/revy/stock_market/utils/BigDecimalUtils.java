package com.revy.stock_market.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {



    /**
     * 두 개의 BigDecimal을 비교하여 첫 번째 값이 두 번째 값보다 크면 1을 반환
     * 첫 번째 값이 두 번째 값보다 작으면 -1을 반환
     * 두 값이 같으면 0을 반환
     * @param a
     * @param b
     * @return
     */
    public static int compare(BigDecimal a, BigDecimal b) {
        return a.compareTo(b);
    }



    /**
     * a == b -> true
     * @param a
     * @param b
     * @return
     */
    public static boolean isEqual(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 0;
    }

    // a > b = true

    /**
     * a > b = true
     * @param a
     * @param b
     * @return
     */
    public static boolean isGreaterThan(BigDecimal a, BigDecimal b) {
        return compare(a, b) == 1;
    }

    /**
     * a < b = true
     * @param a
     * @param b
     * @return
     */
    public static boolean isLessThan(BigDecimal a, BigDecimal b) {
        return compare(a, b) == -1;
    }
}
