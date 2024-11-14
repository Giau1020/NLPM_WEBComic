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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.SalesStatisticsDTO;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.PdfService;
import com.example.demo.Service.ReportService;
import com.example.demo.Service.RevenueService;
import com.example.demo.repository.SalesDetailRepository;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private SalesDetailRepository salesDetailRepository;

    @Autowired
    private ReportService reportService;

    private OrderService orderService;

    @Autowired
    private RevenueService revenueService;

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
//    @GetMapping("/admin/export-revenue-pdf")
//    public ResponseEntity<InputStreamResource> exportRevenueReportPDF(
//            @RequestParam int year) throws DocumentException, IOException {
//
//        // Lấy ngày hiện tại
//        LocalDate reportDate = LocalDate.now();
//
//        // Lấy dữ liệu báo cáo từ ReportService
//        double totalRevenue = reportService.getTotalRevenue(year);
//        int totalOrders = reportService.getTotalOrders(year);
//        int totalComicsSold = reportService.getTotalComicsSold(year);
//        List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
//        List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);
//
//        // Gọi PdfService để tạo file PDF
//        ByteArrayInputStream pdfStream = pdfService.generateRevenueReportPDF(
//                year, reportDate, totalRevenue, totalOrders, totalComicsSold, monthlyRevenue, quarterlyRevenue);
//
//        // Thiết lập header cho response
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=revenue_report.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdfStream));
//    }

//    @GetMapping("/admin/export-revenue-pdf")
//    public ResponseEntity<InputStreamResource> exportRevenueReportPDF(
//            @RequestParam int year) throws DocumentException, IOException {
//
//        // Lấy ngày hiện tại
//        LocalDate reportDate = LocalDate.now();
//
//        // Lấy dữ liệu báo cáo từ ReportService
//        double totalRevenue = reportService.getTotalRevenue(year);
//        int totalOrders = reportService.getTotalOrders(year);
//        int totalComicsSold = reportService.getTotalComicsSold(year);
//        List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
//        List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);
//
//        // Lấy thống kê doanh thu theo sản phẩm
//        List<SalesStatisticsDTO> salesStatistics = revenueService.getSalesStatisticsbyYear(year);
//
//        // Gọi PdfService để tạo file PDF
//        ByteArrayInputStream pdfStream = pdfService.generateRevenueReportPDF(
//                year, reportDate, totalRevenue, totalOrders, totalComicsSold, monthlyRevenue, quarterlyRevenue, salesStatistics);
//
//        // Thiết lập header cho response
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=revenue_report.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdfStream));
//    }
    /////////////////////////////////////////


//    @GetMapping("/admin/export-revenue-pdf")
//    public ResponseEntity<InputStreamResource> exportRevenueReportPDF(@RequestParam int year) {
//        // Lấy ngày hiện tại
//        LocalDate reportDate = LocalDate.now();
//
//        try {
//            // Lấy dữ liệu báo cáo từ ReportService
//            double totalRevenue = reportService.getTotalRevenue(year);
//            int totalOrders = reportService.getTotalOrders(year);
//            int totalComicsSold = reportService.getTotalComicsSold(year);
//            List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
//            List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);
//
//            // Lấy thống kê doanh thu theo sản phẩm
//            List<SalesStatisticsDTO> salesStatistics = revenueService.getSalesStatistics();
//
//            // Kiểm tra nếu có dữ liệu hợp lệ
//            if (salesStatistics == null || monthlyRevenue == null || quarterlyRevenue == null) {
//                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//            }
//
//            // Gọi PdfService để tạo file PDF
//            ByteArrayInputStream pdfStream = pdfService.generateRevenueReportPDF(
//                    year, reportDate, totalRevenue, totalOrders, totalComicsSold, monthlyRevenue, quarterlyRevenue, salesStatistics);
//
//            // Thiết lập header cho response
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "inline; filename=revenue_report.pdf");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(new InputStreamResource(pdfStream));
//        } catch (DocumentException | IOException e) {
//            // Xử lý lỗi nếu có vấn đề khi tạo báo cáo PDF
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new InputStreamResource(new ByteArrayInputStream("Error generating report".getBytes())));
//        }
//    }

//
//    @GetMapping("/admin/export-revenue-pdf")
//    public ResponseEntity<InputStreamResource> exportRevenueReportPDF(
//            @RequestParam int year) throws DocumentException, IOException {
//
//        // Lấy ngày hiện tại
//        LocalDate reportDate = LocalDate.now();
//
//        // Lấy dữ liệu báo cáo từ ReportService
//        double totalRevenue = reportService.getTotalRevenue(year);
//        int totalOrders = reportService.getTotalOrders(year);
//        int totalComicsSold = reportService.getTotalComicsSold(year);
//        List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
//        List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);
//        List<SalesStatisticsDTO> salesStatistics = revenueService.getSalesStatistics();
//
//        // Kiểm tra dữ liệu có hợp lệ không
//        if (monthlyRevenue == null || monthlyRevenue.isEmpty()) {
//            throw new IllegalArgumentException("No monthly revenue data found for year: " + year);
//        }
//        if (quarterlyRevenue == null || quarterlyRevenue.isEmpty()) {
//            throw new IllegalArgumentException("No quarterly revenue data found for year: " + year);
//        }
//        if (salesStatistics == null || salesStatistics.isEmpty()) {
//            throw new IllegalArgumentException("No sales statistics data found for year: " + year);
//        }
//
//        // Gọi PdfService để tạo file PDF
//        ByteArrayInputStream pdfStream = pdfService.generateRevenueReportPDF(
//                year, reportDate, totalRevenue, totalOrders, totalComicsSold, monthlyRevenue, quarterlyRevenue, salesStatistics);
//
//        // Thiết lập header cho response
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline; filename=revenue_report.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(new InputStreamResource(pdfStream));
//    }


//     @GetMapping("/admin/export-revenue-pdf")
//     public ResponseEntity<?> exportRevenueReportPDF(@RequestParam int year) {
//         // Lấy ngày hiện tại
//         LocalDate reportDate = LocalDate.now();

//         try {
//             // Lấy dữ liệu báo cáo từ ReportService
//             double totalRevenue = reportService.getTotalRevenue(year);
//             int totalOrders = reportService.getTotalOrders(year);
//             int totalComicsSold = reportService.getTotalComicsSold(year);
//             List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
//             List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);

//             // Lấy thống kê doanh thu theo sản phẩm
//             List<SalesStatisticsDTO> salesStatistics = revenueService.getSalesStatistics();

//             // Kiểm tra xem có dữ liệu hợp lệ hay không
//             boolean hasData = !salesStatistics.isEmpty() || !monthlyRevenue.isEmpty() || !quarterlyRevenue.isEmpty();

//             // Nếu không có dữ liệu, vẫn tiếp tục tạo PDF với thông báo "Không có dữ liệu"
//             ByteArrayInputStream pdfStream;
//             if (hasData) {
//                 // Gọi PdfService để tạo file PDF với dữ liệu thực tế
//                 pdfStream = pdfService.generateRevenueReportPDF(
//                         year, reportDate, totalRevenue, totalOrders, totalComicsSold, monthlyRevenue, quarterlyRevenue, salesStatistics);
//             } else {
//                 // Gọi PdfService để tạo một PDF trống với thông báo về việc không có dữ liệu
//                 // pdfStream = pdfService.generateEmptyRevenueReportPDF(year, reportDate);
//                 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                         .body("Invalid request. Please check the input data.");
//             }

//             // Thiết lập header cho response
//             HttpHeaders headers = new HttpHeaders();
//             headers.add("Content-Disposition", "inline; filename=revenue_report.pdf");

//             return ResponseEntity.ok()
//                     .headers(headers)
//                     .contentType(MediaType.APPLICATION_PDF)
//                     .body(new InputStreamResource(pdfStream));

//         } catch (DocumentException | IOException e) {
//             // Xử lý lỗi nếu có vấn đề khi tạo báo cáo PDF
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body(new InputStreamResource(new ByteArrayInputStream("Error generating report".getBytes())));
//         }
//     }

// }

@GetMapping("/admin/export-revenue-pdf")
    public ResponseEntity<?> exportRevenueReportPDF(@RequestParam(required = true) int year) {
        // Lấy ngày hiện tại
        LocalDate reportDate = LocalDate.now();

        try {
            // Kiểm tra dữ liệu đầu vào hợp lệ (năm không hợp lệ)
            if (year <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid year. Please provide a valid year.");
            }

            // Lấy dữ liệu báo cáo từ ReportService
            double totalRevenue = reportService.getTotalRevenue(year);
            int totalOrders = reportService.getTotalOrders(year);
            int totalComicsSold = reportService.getTotalComicsSold(year);
            List<Map<String, Object>> monthlyRevenue = reportService.getAllMonthlyRevenue(year);
            List<Map<String, Object>> quarterlyRevenue = reportService.getAllQuarterlyRevenue(year);

            // Lấy thống kê doanh thu theo sản phẩm
            List<SalesStatisticsDTO> salesStatistics = revenueService.getSalesStatistics();

            // Kiểm tra xem có dữ liệu hợp lệ hay không
            boolean hasData = !salesStatistics.isEmpty() && !monthlyRevenue.isEmpty() && !quarterlyRevenue.isEmpty();

            // Nếu không có dữ liệu, trả về Bad Request với thông báo
            if (!hasData) {

//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("No data available for the given year.");
                return ResponseEntity.ok("No data available for the given year.");
            }

            // Nếu có dữ liệu hợp lệ, tạo PDF
            ByteArrayInputStream pdfStream = pdfService.generateRevenueReportPDF(
                    year, reportDate, totalRevenue, totalOrders, totalComicsSold, monthlyRevenue, quarterlyRevenue, salesStatistics);

            // Thiết lập header cho response
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=revenue_report.pdf");

            // Trả về PDF
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(pdfStream));

        } catch (DocumentException | IOException e) {
            // Xử lý lỗi nếu có vấn đề khi tạo báo cáo PDF
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating report: " + e.getMessage());
        }
    }
}
