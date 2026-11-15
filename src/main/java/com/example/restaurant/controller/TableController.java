package com.example.restaurant.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restaurant.entity.CustomerOrder;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.service.TableService;

@Controller
@RequestMapping("/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    /** 機能④：席の一覧 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("tables", tableService.findAllWithOrder());
        return "tables/list";
    }

    /** 機能④：来店受付 → 注文画面へ */
    @PostMapping("/{id}/open")
    public String open(@PathVariable Long id, @RequestParam(name = "guestCount", required = false) Integer guestCount,
            RedirectAttributes ra) {
        try {
            CustomerOrder order = tableService.open(id, guestCount, LocalDateTime.now());
            ra.addFlashAttribute("message", order.getDiningTable().getName() + "に" + guestCount + "名様をご案内しました");
            return "redirect:/orders/" + order.getId();
        } catch (BusinessException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/tables";
        }
    }
}
