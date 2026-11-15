package com.example.restaurant.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.service.KitchenService;

@Controller
@RequestMapping("/kitchen")
public class KitchenController {

    private final KitchenService kitchenService;

    public KitchenController(KitchenService kitchenService) {
        this.kitchenService = kitchenService;
    }

    /** 機能⑤：キッチン画面（まだ提供していない料理） */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", kitchenService.findWaitingItems());
        model.addAttribute("now", LocalDateTime.now());
        return "kitchen/list";
    }

    // TODO 課題A-3：提供済みにする処理を追加する
    //   POST /kitchen/{itemId}/serve
    //   ・kitchenService.serve(itemId, LocalDateTime.now()) を呼ぶ
    //   ・成功したら「（席名）の「（メニュー名）」を提供しました」を "message"、
    //     BusinessException が出たらそのメッセージを "errorMessage" としてフラッシュに入れる
    //   ・/kitchen にリダイレクトする
}
