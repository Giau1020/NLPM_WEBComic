package com.example.demo.controller;

import com.example.demo.DTO.SalesStatisticsDTO;
import com.example.demo.Service.RevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/revenue")
public class RevenueController {

    @Autowired
    private RevenueService revenueService;

    // Lấy thống kê doanh thu tổng quan theo sản phẩm
    @GetMapping("/sales-statistics")
    public List<SalesStatisticsDTO> getSalesStatistics() {
        return revenueService.getSalesStatistics();
    }

    // Lấy thống kê doanh thu theo khoảng thời gian
//    @GetMapping("/sales-statistics/by-time")
//    public List<SalesStatisticsDTO> getSalesStatisticsByTime(
//            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
//            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
//        return revenueService.getSalesStatisticsByTime(startDate, endDate);
//    }
}

