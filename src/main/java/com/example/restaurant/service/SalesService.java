package com.example.restaurant.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.dto.MenuSalesRow;
import com.example.restaurant.dto.SalesSummary;
import com.example.restaurant.entity.CustomerOrder;
import com.example.restaurant.entity.OrderStatus;
import com.example.restaurant.repository.CustomerOrderRepository;
import com.example.restaurant.repository.OrderItemRepository;

@Service
@Transactional(readOnly = true)
public class SalesService {

    private final CustomerOrderRepository customerOrderRepository;
    private final OrderItemRepository orderItemRepository;

    public SalesService(CustomerOrderRepository customerOrderRepository, OrderItemRepository orderItemRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * 機能⑧：1日分の売上集計
     * ・対象はその日（0:00〜翌日0:00未満）に会計された注文
     * ・件数、売上合計（税込み）、1件あたりの平均（切り捨て。0件なら0）
     * ・メニュー別売上（売れた数の多い順）
     */
    public SalesSummary summarize(LocalDate date) {
        // TODO 課題B-3：上のコメントのとおりに集計する
        //   ・その日の 0:00 は date.atStartOfDay()、翌日の 0:00 は date.plusDays(1).atStartOfDay()
        //   ・会計された注文：customerOrderRepository.findByStatusAndPaidAtGreaterThanEqualAndPaidAtLessThanOrderByPaidAtAsc(...)
        //   ・メニュー別売上：orderItemRepository.sumByMenu(...)（JPQL はもう書いてある。読んでみよう）
        //   ・平均は「合計 ÷ 件数」。件数が0のときに0で割らないこと
        return new SalesSummary(date, 0, 0, 0, List.of());
    }
}
