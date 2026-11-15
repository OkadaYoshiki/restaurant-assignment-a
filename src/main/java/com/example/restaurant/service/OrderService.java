package com.example.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.entity.CustomerOrder;
import com.example.restaurant.entity.DiningTable;
import com.example.restaurant.entity.MenuItem;
import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.entity.OrderItemStatus;
import com.example.restaurant.entity.OrderStatus;
import com.example.restaurant.entity.TableStatus;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.repository.CustomerOrderRepository;
import com.example.restaurant.repository.DiningTableRepository;
import com.example.restaurant.repository.MenuItemRepository;
import com.example.restaurant.repository.OrderItemRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {

    /** 1回に注文できる数量の上限 */
    public static final int MAX_QUANTITY = 10;
    /** 消費税率（%） */
    public static final int TAX_RATE_PERCENT = 10;

    private final CustomerOrderRepository customerOrderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final DiningTableRepository diningTableRepository;

    public OrderService(CustomerOrderRepository customerOrderRepository, OrderItemRepository orderItemRepository,
            MenuItemRepository menuItemRepository, DiningTableRepository diningTableRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.diningTableRepository = diningTableRepository;
    }

    public CustomerOrder findById(Long id) {
        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("注文が見つかりません（ID: " + id + "）"));
    }

    public List<OrderItem> findItems(Long orderId) {
        return orderItemRepository.findByCustomerOrderIdOrderByIdAsc(orderId);
    }

    /**
     * 明細の合計（税抜き）
     * TODO 課題B-2：明細ごとの金額（OrderItem#getAmount）を全部足した値を返す
     */
    public static int calcSubtotal(List<OrderItem> items) {
        return 0;
    }

    /**
     * 消費税（1円未満は切り捨て）
     * TODO 課題B-2：subtotal の TAX_RATE_PERCENT % を返す。1円未満は切り捨て
     *   ヒント：int どうしの割り算は小数点以下が切り捨てられます
     */
    public static int calcTax(int subtotal) {
        return 0;
    }

    /**
     * 機能⑥：注文の追加
     * ・注文が存在し、来店中（会計前）であること
     * ・メニューが存在し、品切れでないこと
     * ・数量が1〜MAX_QUANTITY であること
     * ・単価はメニューの「今の価格」を明細にコピーして保存する
     */
    @Transactional
    public OrderItem addItem(Long orderId, Long menuItemId, Integer quantity, LocalDateTime now) {
        // TODO 課題B-1：上のコメントのルールどおりに実装する
        //   エラーのときは BusinessException を投げる。メッセージ例：
        //     「会計済みの注文には追加できません」「「（メニュー名）」は品切れです」「数量は1〜10で注文してください」
        //   ヒント：注文は findById(orderId)、メニューは menuItemRepository.findById(...).orElseThrow(...)
        throw new UnsupportedOperationException("課題B-1：未実装です");
    }

    /**
     * 機能⑦：会計
     * ・注文が来店中（会計前）であること
     * ・明細が1件以上あること
     * ・まだ提供していない料理がないこと
     * ・小計・消費税・合計を計算して注文に記録し、会計済みにする
     * ・席を空席に戻す
     */
    @Transactional
    public CustomerOrder checkout(Long orderId, LocalDateTime now) {
        // TODO 課題B-2：上のコメントのルールどおりに実装する
        //   エラーのときは BusinessException を投げる。メッセージ例：
        //     「この注文はもう会計済みです」「注文がないため会計できません」「まだ提供していない料理があるため会計できません」
        //   ヒント：明細は findItems(orderId)、提供待ちがあるかは orderItemRepository.existsByCustomerOrderIdAndStatus(...)
        //          席は order.getDiningTable() でたどれる
        throw new UnsupportedOperationException("課題B-2：未実装です");
    }
}
