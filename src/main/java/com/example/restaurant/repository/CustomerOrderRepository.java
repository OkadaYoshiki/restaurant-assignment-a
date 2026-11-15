package com.example.restaurant.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.CustomerOrder;
import com.example.restaurant.entity.OrderStatus;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    /** 席の、指定した状態の注文（来店中の注文を探すのに使う） */
    Optional<CustomerOrder> findByDiningTableIdAndStatus(Long tableId, OrderStatus status);

    /** 指定した期間に会計された注文（from 以上、to 未満） */
    List<CustomerOrder> findByStatusAndPaidAtGreaterThanEqualAndPaidAtLessThanOrderByPaidAtAsc(
            OrderStatus status, LocalDateTime from, LocalDateTime to);
}
