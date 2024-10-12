package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
      List<Order> findByUserId(Long userId);
       Optional<Order> findByIdAndUserId(Long orderId, Long userId);
     
}
