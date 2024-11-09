package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    Optional<Order> findById(long id);

    // Truy vấn các đơn hàng đã thanh toán trong một khoảng thời gian nhất định
    @Query("SELECT o FROM Order o WHERE o.orderTime >= :startDate AND o.orderTime <= :endDate AND o.orderStatus = 'Paid'")
    List<Order> findOrdersByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
