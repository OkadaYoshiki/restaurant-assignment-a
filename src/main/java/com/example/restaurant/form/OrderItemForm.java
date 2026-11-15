package com.example.restaurant.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * 注文追加フォーム
 */
public class OrderItemForm {

    @NotNull(message = "メニューを選んでください")
    private Long menuItemId;

    @NotNull(message = "数量を入力してください")
    @Min(value = 1, message = "数量は1以上で入力してください")
    @Max(value = 10, message = "数量は10以下で入力してください")
    private Integer quantity = 1;

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
