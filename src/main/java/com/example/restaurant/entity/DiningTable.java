package com.example.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * 席テーブル（dining_tables）に対応するエンティティ
 */
@Entity
@Table(name = "dining_tables")
public class DiningTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 席の名前（例：A1） */
    @Column(nullable = false, unique = true, length = 20)
    private String name;

    /** 座れる人数 */
    @Column(nullable = false)
    private Integer seats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TableStatus status = TableStatus.EMPTY;

    public boolean isEmpty() {
        return status == TableStatus.EMPTY;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
