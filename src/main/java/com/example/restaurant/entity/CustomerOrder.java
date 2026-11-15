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
 * 注文テーブル（orders）に対応するエンティティ
 * お客さんが席に座ってから会計するまでを1件とする（伝票1枚のイメージ）
 * ※ Order は SQL の予約語（ORDER BY）と紛らわしいので、クラス名は CustomerOrder にしている
 */
@Entity
@Table(name = "orders")
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "table_id")
    private DiningTable diningTable;

    /** 人数 */
    @Column(name = "guest_count", nullable = false)
    private Integer guestCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.OPEN;

    /** 来店日時 */
    @Column(name = "opened_at", nullable = false)
    private LocalDateTime openedAt;

    /** 会計日時（来店中は null） */
    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    /** 小計（税抜き）。会計時に記録する */
    @Column
    private Integer subtotal;

    /** 消費税。会計時に記録する */
    @Column
    private Integer tax;

    /** 合計（税込み）。会計時に記録する */
    @Column
    private Integer total;

    public boolean isOpen() {
        return status == OrderStatus.OPEN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(LocalDateTime openedAt) {
        this.openedAt = openedAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
