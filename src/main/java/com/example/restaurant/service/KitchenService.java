package com.example.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.entity.OrderItemStatus;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.repository.OrderItemRepository;

@Service
@Transactional(readOnly = true)
public class KitchenService {

    private final OrderItemRepository orderItemRepository;

    public KitchenService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * 機能⑤：まだ提供していない料理（注文の古い順）
     * TODO 課題A-3：Repository に追加したメソッドで、状態が ORDERED（注文済）の明細を取得して返す
     */
    public List<OrderItem> findWaitingItems() {
        return List.of();
    }

    /**
     * 機能⑤：提供済みにする
     * TODO 課題A-3：次のとおりに実装する
     *   1. orderItemRepository.findById で明細を取ってくる（なければ BusinessException「注文が見つかりません」）
     *   2. もう提供済みなら BusinessException（「（メニュー名）」はもう提供済みです）
     *   3. 状態を SERVED に、servedAt を now にして保存し、保存した明細を返す
     */
    @Transactional
    public OrderItem serve(Long orderItemId, LocalDateTime now) {
        throw new UnsupportedOperationException("課題A-3：未実装です");
    }
}
