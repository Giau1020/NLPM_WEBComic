//package com.example.demo.controller;
//
//import com.example.demo.Service.ReportService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/reports")
//public class ReportController {
//
//    @Autowired
//    private ReportService reportService;
//
//    @GetMapping("/monthly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getMonthlyRevenue() {
//        return new ResponseEntity<>(reportService.getMonthlyRevenue(), HttpStatus.OK);
//    }
//
//    @GetMapping("/quarterly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenue() {
//        return new ResponseEntity<>(reportService.getQuarterlyRevenue(), HttpStatus.OK);
//    }
//
//    @GetMapping("/monthly-quantity")
//    public ResponseEntity<List<Map<String, Object>>> getMonthlyQuantity() {
//        return new ResponseEntity<>(reportService.getMonthlyQuantity(), HttpStatus.OK);
//    }
//}
package com.example.demo.controller;

import com.example.demo.DTO.DailyRevenueDTO;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    private OrderService orderService;

    @Autowired
    public ReportController(OrderService orderService) {
        this.orderService = orderService;
    }
    // API lấy doanh thu theo tháng
    // API lấy doanh thu cho tất cả các tháng trong năm
//    @GetMapping("/monthly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getMonthlyRevenue(@RequestParam int year) {
//        List<Map<String, Object>> monthlyRevenue = reportService.getMonthlyRevenue(year);
//        return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);
//    }
//
//    // API lấy doanh thu cho tất cả các quý trong năm
//    @GetMapping("/quarterly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenue(@RequestParam int year) {
//        List<Map<String, Object>> quarterlyRevenue = reportService.getQuarterlyRevenue(year);
//        return new ResponseEntity<>(quarterlyRevenue, HttpStatus.OK);
//    }
    // lấy từng tháng
    @GetMapping("/v1/monthly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getMonthlyRevenue(@RequestParam int year, @RequestParam int month) {
        List<Map<String, Object>> monthlyRevenue = reportService.getMonthlyRevenue(year, month);
        return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);
    }
    //lấy từng quý
    @GetMapping("/v1/quarterly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenue(@RequestParam int year, @RequestParam int quarter) {
        List<Map<String, Object>> quarterlyRevenue = reportService.getQuarterlyRevenue(year, quarter);
        return new ResponseEntity<>(quarterlyRevenue, HttpStatus.OK);
    }

    // API lấy doanh thu cho tất cả các tháng trong năm
    @GetMapping("/monthly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getAllMonthlyRevenue(@RequestParam int year) {
        List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
        return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);
    }

    // API lấy doanh thu cho tất cả các quý trong năm
    @GetMapping("/quarterly-revenue")
    public ResponseEntity<List<Map<String, Object>>> getAllQuarterlyRevenue(@RequestParam int year) {
        List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);
        return new ResponseEntity<>(quarterlyRevenue, HttpStatus.OK);
    }

//    @GetMapping("/v1/monthly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getMonthlyRevenue() {
//        return new ResponseEntity<>(reportService.getMonthlyRevenue(), HttpStatus.OK);
//    }
//
//    @GetMapping("/v1/quarterly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenue() {
//        return new ResponseEntity<>(reportService.getQuarterlyRevenue(), HttpStatus.OK);
//    }
//
//    @GetMapping("/v1/monthly-quantity")
//    public ResponseEntity<List<Map<String, Object>>> getMonthlyQuantity() {
//        return new ResponseEntity<>(reportService.getMonthlyQuantity(), HttpStatus.OK);
//    }

    // Endpoint để lấy doanh thu theo tháng (theo ngày trong tháng)
    @GetMapping("/admin/orders/revenue")
    public List<Map<String, Object>> getMonthlyRevenueByDay(
            @RequestParam int year,
            @RequestParam int month) {
        return orderService.getMonthlyRevenueByDay(year, month);
    }

// lấy doanh thu theo quý ( theo từng tháng)
//    @GetMapping("/admin/quarterly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenueByMonth(
//            @RequestParam("year") int year,
//            @RequestParam("quarter") int quarter) {
//
//        List<Map<String, Object>> revenueData = reportService.getQuarterlyRevenueByMonth(year, quarter);
//
//        return ResponseEntity.ok(revenueData);
//    }
    /**
     * API để lấy doanh thu theo quý, phân theo tháng trong quý
     *
     * @param year: Năm cần lấy doanh thu
     * @param quarter: Quý (1, 2, 3, 4)
     * @return danh sách doanh thu phân theo tháng trong quý
     */
//    @GetMapping("/admin/quarterly-revenue")
//    public ResponseEntity<List<Map<String, Object>>> getQuarterlyRevenueByMonth(
//            @RequestParam("year") int year,
//            @RequestParam("quarter") int quarter) {
//
//        List<Map<String, Object>> revenueData = reportService.getQuarterlyRevenueByMonth(year, quarter);
//
//        return ResponseEntity.ok(revenueData);
//    }
    @GetMapping("/admin/quarterly-revenue")
    public List<Map<String, Object>> getQuarterlyRevenueByMonth(@RequestParam("year") int year, @RequestParam("quarter") int quarter) {
        return orderService.getQuarterlyRevenueByMonth(year, quarter);
    }

}
