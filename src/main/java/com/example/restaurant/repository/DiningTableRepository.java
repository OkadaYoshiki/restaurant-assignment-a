package com.example.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restaurant.entity.DiningTable;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {

    List<DiningTable> findAllByOrderByIdAsc();
}
