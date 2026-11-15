package com.example.restaurant.entity;

/**
 * 注文した料理の状態
 */
public enum OrderItemStatus {
    ORDERED("注文済"),
    SERVED("提供済");

    private final String label;

    OrderItemStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
