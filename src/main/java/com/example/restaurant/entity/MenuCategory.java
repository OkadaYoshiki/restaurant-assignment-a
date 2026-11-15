package com.example.restaurant.entity;

/**
 * メニューの種類
 */
public enum MenuCategory {
    MAIN("メイン"),
    SIDE("サイド"),
    DRINK("ドリンク"),
    DESSERT("デザート");

    private final String label;

    MenuCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
