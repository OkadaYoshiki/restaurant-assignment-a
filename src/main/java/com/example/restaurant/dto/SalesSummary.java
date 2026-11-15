package com.example.restaurant.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * 1日分の売上集計
 */
public class SalesSummary {

    /** 集計した日 */
    private final LocalDate date;

    /** 会計した件数 */
    private final int orderCount;

    /** 売上合計（税込み） */
    private final int totalSales;

    /** 1件あたりの平均（税込み、1円未満切り捨て） */
    private final int average;

    /** メニュー別売上（売れた数の多い順） */
    private final List<MenuSalesRow> ranking;

    public SalesSummary(LocalDate date, int orderCount, int totalSales, int average, List<MenuSalesRow> ranking) {
        this.date = date;
        this.orderCount = orderCount;
        this.totalSales = totalSales;
        this.average = average;
        this.ranking = ranking;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public int getAverage() {
        return average;
    }

    public List<MenuSalesRow> getRanking() {
        return ranking;
    }
}
