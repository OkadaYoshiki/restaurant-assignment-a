package com.example.restaurant.entity;

/**
 * 注文（来店）の状態
 */
public enum OrderStatus {
    OPEN("来店中"),
    PAID("会計済");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
