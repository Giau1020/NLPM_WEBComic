package com.example.demo.Service;

import com.example.demo.DTO.DailyRevenueDTO;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;



    // Lấy doanh thu cho tất cả các tháng trong năm
    public List<Map<String, Object>> getAllMonthlyRevenue(int year) {
        List<Object[]> result = orderRepository.getAllMonthlyRevenue(year);
        List<Map<String, Object>> revenueList = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> data = new HashMap<>();
            // Kiểm tra dữ liệu trả về và sử dụng đúng chỉ số
            if (row != null && row.length >= 2) {
                data.put("month", row[0]);  // row[0] chứa tháng
                data.put("totalRevenue", row[1]);  // row[1] chứa doanh thu
            }
            revenueList.add(data);
        }
        return revenueList;
    }

    // Lấy doanh thu cho tất cả các quý trong năm
    public List<Map<String, Object>> getAllQuarterlyRevenue(int year) {
        List<Object[]> result = orderRepository.getAllQuarterlyRevenue(year);
        List<Map<String, Object>> revenueList = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> data = new HashMap<>();
            // Kiểm tra dữ liệu trả về và sử dụng đúng chỉ số
            if (row != null && row.length >= 2) {
                data.put("quarter", row[0]);  // row[0] chứa quý
                data.put("totalRevenue", row[1]);  // row[1] chứa doanh thu
            }
            revenueList.add(data);
        }
        return revenueList;
    }

    // Phương thức lấy doanh thu theo tháng
    public List<Map<String, Object>> getMonthlyRevenue(int year, int month) {
        List<Object[]> results = orderRepository.findMonthlyRevenue(year, month);
        List<Map<String, Object>> monthlyRevenue = new ArrayList<>();

        if (results.isEmpty()) {
            // Nếu không có dữ liệu trả về, thêm tháng và doanh thu là 0
            Map<String, Object> data = new HashMap<>();
            data.put("year", year);
            data.put("month", month);
            data.put("totalRevenue", 0);
            monthlyRevenue.add(data);
        } else {
            for (Object[] result : results) {
                Map<String, Object> data = new HashMap<>();
                data.put("year", result[0]);
                data.put("month", result[1]);
                data.put("totalRevenue", result[2]);
                monthlyRevenue.add(data);
            }
        }

        return monthlyRevenue;
    }


    // Phương thức lấy doanh thu theo quý
    public List<Map<String, Object>> getQuarterlyRevenue(int year, int quarter) {
        List<Object[]> results = orderRepository.findQuarterlyRevenue(year, quarter);
        List<Map<String, Object>> quarterlyRevenue = new ArrayList<>();

        if (results.isEmpty()) {
            // Nếu không có dữ liệu trả về, thêm quý và doanh thu là 0
            Map<String, Object> data = new HashMap<>();
            data.put("year", year);
            data.put("quarter", quarter);
            data.put("totalRevenue", 0);
            quarterlyRevenue.add(data);
        } else {
            for (Object[] result : results) {
                Map<String, Object> data = new HashMap<>();
                data.put("year", result[0]);
                data.put("quarter", result[1]);
                data.put("totalRevenue", result[2]);
                quarterlyRevenue.add(data);
            }
        }

        return quarterlyRevenue;
    }


    // Phương thức lấy số lượng sản phẩm bán ra theo tháng
    public List<Map<String, Object>> getMonthlyQuantity(int year, int month) {
        List<Object[]> results = orderItemRepository.findMonthlyQuantity(year, month);
        List<Map<String, Object>> monthlyQuantity = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            data.put("year", result[0]);
            data.put("month", result[1]);
            data.put("totalQuantity", result[2]);
            monthlyQuantity.add(data);
        }

        return monthlyQuantity;
    }
// Phương thức lấy doanh thu theo tháng (theo ngày trong tháng)
//public ReportService(OrderRepository orderRepository) {
//    this.orderRepository = orderRepository;
//}

//    /**
//     * Lấy doanh thu theo quý phân theo tháng trong quý
//     *
//     * @param year: Năm cần lấy doanh thu
//     * @param quarter: Quý (1, 2, 3, 4)
//     * @return danh sách doanh thu phân theo tháng trong quý
//     */
//    public List<Map<String, Object>> getQuarterlyRevenueByMonth(int year, int quarter) {
//        return orderRepository.findQuarterlyRevenueByMonth(year, quarter);
//    }

    /**
     * Lấy doanh thu theo quý phân theo tháng trong quý, xử lý trường hợp không có doanh thu.
     *
     * @param year: Năm cần lấy doanh thu
     * @param quarter: Quý (1, 2, 3, 4)
     * @return danh sách doanh thu phân theo tháng trong quý, với các tháng không có doanh thu sẽ trả về 0
     */
//    public List<Map<String, Object>> getQuarterlyRevenueByMonth(int year, int quarter) {
//        // Lấy doanh thu theo quý phân theo tháng
//        List<Map<String, Object>> result = orderRepository.findQuarterlyRevenueByMonth(year, quarter);
//
//        // Tạo một Map với các tháng trong quý
//        Map<Integer, Double> monthsRevenueMap = new HashMap<>();
//        for (int month = 1; month <= 3; month++) {
//            monthsRevenueMap.put(month, 0.0); // Khởi tạo tất cả các tháng trong quý với giá trị 0
//        }
//
//        // Điền dữ liệu doanh thu vào Map
//        for (Map<String, Object> data : result) {
//            Integer month = (Integer) data.get("month");
//            Double totalRevenue = (Double) data.get("totalRevenue");
//            monthsRevenueMap.put(month, totalRevenue);
//        }
//
//        // Chuyển Map thành List kết quả
//        List<Map<String, Object>> finalResult = new ArrayList<>();
//        for (int month = 1; month <= 3; month++) {
//            Map<String, Object> monthData = new HashMap<>();
//            monthData.put("year", year);
//            monthData.put("month", month);
//            monthData.put("totalRevenue", monthsRevenueMap.get(month));
//            finalResult.add(monthData);
//        }
//
//        return finalResult;
//    }

    // Lấy doanh thu theo quý phân theo tháng
    // Lấy doanh thu theo quý phân theo tháng
//    public List<Map<String, Object>> getQuarterlyRevenueByMonth(int year, int quarter) {
//        // Lấy doanh thu theo quý phân theo tháng
//        List<Object[]> result = orderRepository.findQuarterlyRevenueByMonth(year, quarter);
//
//        // Khởi tạo map với tất cả tháng của quý (quý 4 gồm tháng 10, 11, 12)
//        Map<Integer, Double> monthsRevenueMap = new HashMap<>();
//        for (int month = 1; month <= 3; month++) {
//            monthsRevenueMap.put(month, 0.0); // Khởi tạo tất cả các tháng với giá trị 0.0
//        }
//
//        // Điền doanh thu vào map từ kết quả truy vấn
//        for (Object[] data : result) {
//            Integer month = (Integer) data[0]; // Tháng từ kết quả truy vấn
//            Double totalRevenue = (Double) data[1]; // Doanh thu từ kết quả truy vấn
//            monthsRevenueMap.put(month, totalRevenue);
//        }
//
//        // Chuyển map thành List kết quả để trả về
//        List<Map<String, Object>> finalResult = new ArrayList<>();
//        for (int month = 1; month <= 3; month++) {
//            Map<String, Object> monthData = new HashMap<>();
//            monthData.put("year", year);
//            monthData.put("month", month);
//            monthData.put("totalRevenue", monthsRevenueMap.get(month)); // Nếu không có doanh thu thì trả về 0.0
//            finalResult.add(monthData);
//        }
//
//        return finalResult;
//    }
//    ////// báo cáo doanh thu theo sản phẩm


}
