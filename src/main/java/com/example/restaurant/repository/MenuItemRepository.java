package com.example.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.MenuCategory;
import com.example.restaurant.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByOrderByIdAsc();

    // TODO 課題A-1：指定した種類（category）のメニューを、ID順で取得するメソッドを追加する
    //   ヒント：すぐ上の findAllByOrderByIdAsc と、名前のつけ方を見比べてみよう

    /** 販売中（品切れでない）のメニューを種類・ID順で取得 */
    List<MenuItem> findByAvailableTrueOrderByCategoryAscIdAsc();
}
