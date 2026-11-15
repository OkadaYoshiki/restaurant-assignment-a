package com.example.restaurant.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.form.OrderItemForm;
import com.example.restaurant.service.MenuService;
import com.example.restaurant.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final MenuService menuService;

    public OrderController(OrderService orderService, MenuService menuService) {
        this.orderService = orderService;
        this.menuService = menuService;
    }

    /** 注文画面（注文した料理の一覧と、注文追加フォーム） */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("orderItemForm")) {
            model.addAttribute("orderItemForm", new OrderItemForm());
        }
        addDetail(id, model);
        return "orders/detail";
    }

    /** 機能⑥：注文の追加 */
    @PostMapping("/{id}/items")
    public String addItem(@PathVariable Long id, @Validated @ModelAttribute OrderItemForm orderItemForm,
            BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            addDetail(id, model);
            return "orders/detail";
        }
        try {
            OrderItem item = orderService.addItem(id, orderItemForm.getMenuItemId(), orderItemForm.getQuantity(),
                    LocalDateTime.now());
            ra.addFlashAttribute("message", "「" + item.getMenuItem().getName() + "」を" + item.getQuantity() + "つ注文しました");
        } catch (BusinessException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/orders/" + id;
    }

    /** 機能⑦：会計画面（確認） */
    @GetMapping("/{id}/checkout")
    public String checkoutForm(@PathVariable Long id, Model model) {
        addDetail(id, model);
        return "orders/checkout";
    }

    /** 機能⑦：会計の確定 */
    @PostMapping("/{id}/checkout")
    public String checkout(@PathVariable Long id, RedirectAttributes ra) {
        try {
            orderService.checkout(id, LocalDateTime.now());
            ra.addFlashAttribute("message", "会計が完了しました");
        } catch (BusinessException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/orders/" + id + "/checkout";
    }

    private void addDetail(Long id, Model model) {
        List<OrderItem> items = orderService.findItems(id);
        int subtotal = OrderService.calcSubtotal(items);
        int tax = OrderService.calcTax(subtotal);
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("items", items);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("tax", tax);
        model.addAttribute("total", subtotal + tax);
        model.addAttribute("menus", menuService.findAvailable());
    }
}
