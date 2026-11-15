package com.example.restaurant.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.restaurant.dto.TableView;
import com.example.restaurant.entity.CustomerOrder;
import com.example.restaurant.entity.DiningTable;
import com.example.restaurant.entity.OrderStatus;
import com.example.restaurant.entity.TableStatus;
import com.example.restaurant.exception.BusinessException;
import com.example.restaurant.repository.CustomerOrderRepository;
import com.example.restaurant.repository.DiningTableRepository;

@Service
@Transactional(readOnly = true)
public class TableService {

    private final DiningTableRepository diningTableRepository;
    private final CustomerOrderRepository customerOrderRepository;

    public TableService(DiningTableRepository diningTableRepository,
            CustomerOrderRepository customerOrderRepository) {
        this.diningTableRepository = diningTableRepository;
        this.customerOrderRepository = customerOrderRepository;
    }

    /** 機能④：席の一覧（利用中の席には来店中の注文IDを付ける） */
    public List<TableView> findAllWithOrder() {
        return diningTableRepository.findAllByOrderByIdAsc().stream()
                .map(t -> new TableView(t, customerOrderRepository
                        .findByDiningTableIdAndStatus(t.getId(), OrderStatus.OPEN)
                        .map(CustomerOrder::getId).orElse(null)))
                .toList();
    }

    /**
     * 機能④：来店受付
     * ・席が空席であること
     * ・人数が1人以上、席の人数以下であること
     * ・注文（来店中）を作り、席を利用中にする
     */
    @Transactional
    public CustomerOrder open(Long tableId, Integer guestCount, LocalDateTime now) {
        DiningTable table = diningTableRepository.findById(tableId)
                .orElseThrow(() -> new BusinessException("席が見つかりません"));
        if (!table.isEmpty()) {
            throw new BusinessException(table.getName() + "は利用中です");
        }
        if (guestCount == null || guestCount < 1 || guestCount > table.getSeats()) {
            throw new BusinessException(table.getName() + "は1〜" + table.getSeats() + "人で利用できます");
        }
        CustomerOrder order = new CustomerOrder();
        order.setDiningTable(table);
        order.setGuestCount(guestCount);
        order.setStatus(OrderStatus.OPEN);
        order.setOpenedAt(now);

        table.setStatus(TableStatus.IN_USE);
        diningTableRepository.save(table);
        return customerOrderRepository.save(order);
    }
}
