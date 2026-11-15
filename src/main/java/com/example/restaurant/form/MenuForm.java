package com.example.restaurant.form;

import com.example.restaurant.entity.MenuCategory;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * メニューの登録・編集フォーム
 */
public class MenuForm {

    @NotBlank(message = "メニュー名を入力してください")
    @Size(max = 50, message = "メニュー名は50文字以内で入力してください")
    private String name;

    @NotNull(message = "種類を選んでください")
    private MenuCategory category;

    @NotNull(message = "価格を入力してください")
    @Min(value = 1, message = "価格は1円以上で入力してください")
    @Max(value = 99999, message = "価格は99,999円以下で入力してください")
    private Integer price;

    private boolean available = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
