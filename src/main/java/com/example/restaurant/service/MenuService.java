package com.example.restaurant.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.entity.MenuCategory;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.form.MenuForm;
import com.example.restaurant.repository.MenuItemRepository;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuItem> findAll() {
        return menuItemRepository.findAllByOrderByIdAsc();
    }

    /**
     * 機能①：種類で絞り込み。category が null なら全件
     * TODO 課題A-1：category が null なら findAll() を、そうでなければ Repository に追加したメソッドの結果を返す
     *   （今はいつも全件を返している）
     */
    public List<MenuItem> findByCategory(MenuCategory category) {
        return findAll();
    }

    /**
     * 販売中のメニュー（注文画面の選択肢）
     * 種類（メイン → サイド → ドリンク → デザート）の順に並べる。
     * DB の category 列は文字列なので、ORDER BY だとアルファベット順（DESSERT が先頭）になってしまう。
     * そのため、enum の宣言順で Java 側で並べ替えている
     */
    public List<MenuItem> findAvailable() {
        return menuItemRepository.findByAvailableTrueOrderByIdAsc().stream()
                .sorted(Comparator.comparing(MenuItem::getCategory))
                .toList();
    }

    public MenuItem findById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new BusinessException("メニューが見つかりません（ID: " + id + "）"));
    }

    /** 機能②：メニュー登録 */
    @Transactional
    public MenuItem register(MenuForm form) {
        MenuItem item = new MenuItem();
        copy(form, item);
        return menuItemRepository.save(item);
    }

    /** 機能②：メニュー編集 */
    @Transactional
    public MenuItem update(Long id, MenuForm form) {
        MenuItem item = findById(id);
        copy(form, item);
        return menuItemRepository.save(item);
    }

    /**
     * 機能③：品切れの切り替え（販売中 ⇄ 品切れ）
     * TODO 課題A-2：findById でメニューを取ってきて、available を反対の値にして保存し、保存したメニューを返す
     */
    @Transactional
    public MenuItem toggleAvailable(Long id) {
        throw new UnsupportedOperationException("課題A-2：未実装です");
    }

    public MenuForm toForm(MenuItem item) {
        MenuForm form = new MenuForm();
        form.setName(item.getName());
        form.setCategory(item.getCategory());
        form.setPrice(item.getPrice());
        form.setAvailable(item.getAvailable());
        return form;
    }

    private void copy(MenuForm form, MenuItem item) {
        item.setName(form.getName());
        item.setCategory(form.getCategory());
        item.setPrice(form.getPrice());
        item.setAvailable(form.isAvailable());
    }
}
