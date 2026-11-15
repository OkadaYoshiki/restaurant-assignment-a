package com.example.restaurant.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.restaurant.service.SalesService;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    // TODO 課題B-3：売上集計の画面を追加する
    //   GET /sales
    //   ・日付は @RequestParam(name = "date", required = false) で受け取る（なければ今日 LocalDate.now()）
    //     日付の受け取りには @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) を付ける
    //   ・salesService.summarize(日付) の結果を "summary" という名前で Model に入れる
    //   ・sales/index.html を表示する（テンプレートも自分で作る）
}
