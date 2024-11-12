package com.example.demo.controller;

import com.example.demo.Service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sales")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    // API lấy báo cáo bán hàng theo tháng cho sản phẩm
    @GetMapping("/monthly")
    public ResponseEntity<List<Map<String, Object>>> getMonthlySales(@RequestParam int month,
                                                                     @RequestParam int year,
                                                                     @RequestParam Long comicId) {
        List<Map<String, Object>> sales = salesReportService.getMonthlySales(month, year, comicId);
        return ResponseEntity.ok(sales);
    }

    // API lấy báo cáo bán hàng theo quý cho sản phẩm
    @GetMapping("/quarterly")
    public ResponseEntity<List<Map<String, Object>>> getQuarterlySales(@RequestParam int quarter,
                                                                       @RequestParam int year,
                                                                       @RequestParam Long comicId) {
        List<Map<String, Object>> sales = salesReportService.getQuarterlySales(quarter, year, comicId);
        return ResponseEntity.ok(sales);
    }

    // API lấy báo cáo bán hàng theo năm cho sản phẩm
    @GetMapping("/yearly")
    public ResponseEntity<List<Map<String, Object>>> getYearlySales(@RequestParam int year,
                                                                    @RequestParam Long comicId) {
        List<Map<String, Object>> sales = salesReportService.getYearlySales(year, comicId);
        return ResponseEntity.ok(sales);
    }
    // API để lấy số lượng bán theo ngày của một comicId trong tháng
    @GetMapping("/daily/comic/{comicId}")
    public ResponseEntity<List<Map<String, Object>>> getDailySalesByComicId(
            @PathVariable Long comicId,
            @RequestParam int year,
            @RequestParam int month) {

        List<Map<String, Object>> dailySales = salesReportService.getDailySalesStatisticsByComicId(comicId, year, month);
        return ResponseEntity.ok(dailySales);
    }
    // API để lấy tổng số lượng bán cho mỗi truyện
    @GetMapping("/total-sales")
    public ResponseEntity<List<Map<String, Object>>> getTotalSales() {
        List<Map<String, Object>> totalSales = salesReportService.getTotalSalesByComic();
        return ResponseEntity.ok(totalSales);
    }

//    // API lấy doanh thu bán theo tháng trong quý của một truyện
//    @GetMapping("/quarterly/{comicId}")
//    public ResponseEntity<List<Object[]>> getQuarterlySalesStatistics(
//            @PathVariable Long comicId,
//            @RequestParam int year,
//            @RequestParam int quarter) {
//
//        List<Object[]> salesData = salesReportService.getMonthlySalesStatistics(comicId, year, quarter);
//        return ResponseEntity.ok(salesData);
//    }
    
// API lấy doanh thu bán theo tháng trong quý của một truyện
@GetMapping("/quarterly/{comicId}")
public ResponseEntity<List<Map<String, Object>>> getQuarterlySalesStatistics(
        @PathVariable Long comicId,
        @RequestParam int year,
        @RequestParam int quarter) {

    // Gọi service để lấy dữ liệu doanh thu bán theo tháng trong quý
    List<Map<String, Object>> salesData = salesReportService.getMonthlySalesStatistics(comicId, year, quarter);

    // Trả về dữ liệu dưới dạng ResponseEntity với mã trạng thái 200 (OK)
    return ResponseEntity.ok(salesData);
}


    // API lấy tổng số lượng bán theo tháng trong năm
    @GetMapping("/v1/monthly/{comicId}")
    public ResponseEntity<List<Map<String, Object>>> getMonthlySalesStatistics(
            @PathVariable Long comicId,
            @RequestParam int year) {

        // Gọi service để lấy dữ liệu
        List<Map<String, Object>> salesData = salesReportService.getMonthlySalesStatistics(comicId, year);

        // Trả về kết quả dưới dạng JSON
        return ResponseEntity.ok(salesData);
    }



    // số lượng theo quý trong 1 năm
    @GetMapping("/v1/quarterly/{comicId}")
    public ResponseEntity<List<Map<String, Object>>> getQuarterlySalesStatistics(
            @PathVariable Long comicId,
            @RequestParam int year) {

        List<Map<String, Object>> salesData = salesReportService.getQuarterlySalesStatistics(comicId, year);
        return ResponseEntity.ok(salesData);
    }
}
