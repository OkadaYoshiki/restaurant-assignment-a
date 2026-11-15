package com.example.restaurant.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 注文明細テーブル（order_items）に対応するエンティティ
 * 1件の注文（CustomerOrder）に、注文した料理が何品もぶら下がる
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private CustomerOrder customerOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

    /** 注文したときの単価。メニューの値段が後で変わっても、この値は変わらない */
    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderItemStatus status = OrderItemStatus.ORDERED;

    /** 注文日時 */
    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    /** 提供日時（まだなら null） */
    @Column(name = "served_at")
    private LocalDateTime servedAt;

    /** 金額（単価 × 数量） */
    public int getAmount() {
        return unitPrice * quantity;
    }

    /** 注文してから指定時刻までの経過分（キッチン画面で使う） */
    public long waitingMinutes(LocalDateTime now) {
        return java.time.Duration.between(orderedAt, now).toMinutes();
    }

    public boolean isServed() {
        return status == OrderItemStatus.SERVED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDateTime orderedAt) {
        this.orderedAt = orderedAt;
    }

    public LocalDateTime getServedAt() {
        return servedAt;
    }

    public void setServedAt(LocalDateTime servedAt) {
        this.servedAt = servedAt;
    }
}
