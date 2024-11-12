package com.example.demo.controller;

import com.example.demo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/orders")
public class getOrderController {

    @Autowired
    private OrderService orderService;

    // API để lấy tổng số đơn hàng và tổng doanh thu
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getOrderSummary(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", orderService.getTotalOrders(startDate, endDate));
        summary.put("totalRevenue", orderService.getTotalRevenue(startDate, endDate));

        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    // API để lấy tổng số đơn hàng và tổng doanh thu không theo ngày tháng năm
    @GetMapping("/total-summary")
    public ResponseEntity<Map<String, Object>> getTotalOrderSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalOrders", orderService.getTotalOrders());
        summary.put("totalRevenue", orderService.getTotalRevenue());
        summary.put("totalComicsSold", orderService.getTotalComicsSold()); // Thêm tổng số lượng truyện bán
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }


}
