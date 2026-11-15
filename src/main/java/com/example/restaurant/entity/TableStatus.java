package com.example.restaurant.entity;

/**
 * 席の状態
 */
public enum TableStatus {
    EMPTY("空席"),
    IN_USE("利用中");

    private final String label;

    TableStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
