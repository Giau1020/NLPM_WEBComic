package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ComicWarehouse;

public interface ComicWarehouseRepository extends JpaRepository<ComicWarehouse, Long> {
}
