package com.example.restaurant.dto;

import com.example.restaurant.entity.DiningTable;

/**
 * 席一覧の1行（席と、来店中ならその注文ID）
 */
public class TableView {

    /** 席 */
    private final DiningTable table;

    /** 来店中の注文ID（空席なら null） */
    private final Long orderId;

    public TableView(DiningTable table, Long orderId) {
        this.table = table;
        this.orderId = orderId;
    }

    public DiningTable getTable() {
        return table;
    }

    public Long getOrderId() {
        return orderId;
    }
}
