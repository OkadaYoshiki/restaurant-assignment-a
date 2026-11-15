package com.example.restaurant.dto;

/**
 * メニュー別売上の1行（OrderItemRepository#sumByMenu の結果）
 */
public class MenuSalesRow {

    /** メニュー名 */
    private final String menuName;

    /** 売れた数 */
    private final Long quantity;

    /** 売上金額（税抜き） */
    private final Long amount;

    public MenuSalesRow(String menuName, Long quantity, Long amount) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.amount = amount;
    }

    public String getMenuName() {
        return menuName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getAmount() {
        return amount;
    }
}
