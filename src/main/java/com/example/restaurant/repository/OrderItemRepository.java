package com.example.restaurant.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.restaurant.dto.MenuSalesRow;
import com.example.restaurant.entity.OrderItem;
import com.example.restaurant.entity.OrderItemStatus;
import com.example.restaurant.entity.OrderStatus;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /** 1件の注文の明細を、注文した順に取得 */
    List<OrderItem> findByCustomerOrderIdOrderByIdAsc(Long orderId);

    // TODO 課題A-3：指定した状態（status）の明細を、注文日時（orderedAt）の古い順に取得するメソッドを追加する

    /** 注文の中に、指定した状態の明細が1件でもあるか */
    boolean existsByCustomerOrderIdAndStatus(Long orderId, OrderItemStatus status);

    /**
     * メニュー別の売上（期間内に会計された注文が対象）
     * 売れた数の多い順。メソッド名だけでは書けない集計なので、JPQL を自分で書いている
     */
    @Query("""
            select new com.example.restaurant.dto.MenuSalesRow(
                     m.name, sum(i.quantity), sum(i.unitPrice * i.quantity))
              from OrderItem i
              join i.menuItem m
              join i.customerOrder o
             where o.status = :status
               and o.paidAt >= :from and o.paidAt < :to
             group by m.id, m.name
             order by sum(i.quantity) desc, m.id asc
            """)
    List<MenuSalesRow> sumByMenu(@Param("status") OrderStatus status,
            @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
