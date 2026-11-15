package com.example.restaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restaurant.entity.MenuCategory;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.form.MenuForm;
import com.example.restaurant.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /** 機能①：メニュー一覧・種類で絞り込み */
    @GetMapping
    public String list(@RequestParam(name = "category", required = false) MenuCategory category, Model model) {
        model.addAttribute("items", menuService.findByCategory(category));
        model.addAttribute("categories", MenuCategory.values());
        model.addAttribute("selected", category);
        return "menu/list";
    }

    /** 機能②：メニュー登録（入力画面） */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("menuForm", new MenuForm());
        model.addAttribute("categories", MenuCategory.values());
        return "menu/form";
    }

    /** 機能②：メニュー登録（登録処理） */
    @PostMapping("/new")
    public String create(@Validated @ModelAttribute MenuForm menuForm, BindingResult result,
            Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("categories", MenuCategory.values());
            return "menu/form";
        }
        menuService.register(menuForm);
        ra.addFlashAttribute("message", "メニューを登録しました");
        return "redirect:/menu";
    }

    /** 機能②：メニュー編集（入力画面） */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("menuForm", menuService.toForm(menuService.findById(id)));
        model.addAttribute("menuId", id);
        model.addAttribute("categories", MenuCategory.values());
        return "menu/form";
    }

    /** 機能②：メニュー編集（更新処理） */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, @Validated @ModelAttribute MenuForm menuForm,
            BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("menuId", id);
            model.addAttribute("categories", MenuCategory.values());
            return "menu/form";
        }
        menuService.update(id, menuForm);
        ra.addFlashAttribute("message", "メニューを更新しました");
        return "redirect:/menu";
    }

    // TODO 課題A-2：品切れの切り替えを受け取るメソッドを追加する
    //   POST /menu/{id}/toggle
    //   ・menuService.toggleAvailable(id) を呼ぶ
    //   ・「「（メニュー名）」を品切れにしました」または「〜を販売中にしました」を "message" としてフラッシュに入れる
    //   ・/menu にリダイレクトする
}
