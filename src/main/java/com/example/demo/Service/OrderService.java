package com.example.demo.Service;


import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Tính tổng số lượng truyện bán ra trong toàn bộ hệ thống
    public Long getTotalComicsSold() {
        return orderItemRepository.getTotalComicsSold();
    }

    // Tính tổng số đơn hàng
    public Long getTotalOrders(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getTotalOrders(startDate, endDate);
    }

    // Tính tổng doanh thu
    public Double getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getTotalRevenue(startDate, endDate);
    }

    // Tính tổng số đơn hàng không theo ngày tháng năm
    public Long getTotalOrders() {
        return orderRepository.getTotalOrders();
    }

    // Tính tổng doanh thu không theo ngày tháng năm
    public Double getTotalRevenue() {
        return orderRepository.getTotalRevenue();
    }

//     Phương thức lấy doanh thu theo tháng và ngày
//    public List<Map<String, Object>> getMonthlyRevenueByDay(int year, int month) {
//        List<Object[]> results = orderRepository.findMonthlyRevenueByDay(year, month);
//        List<Map<String, Object>> monthlyRevenue = new ArrayList<>();
//
//        // Nếu không có dữ liệu trả về, thêm tháng và doanh thu là 0
//        if (results.isEmpty()) {
//            Map<String, Object> data = new HashMap<>();
//            data.put("year", year);
//            data.put("month", month);
//            data.put("day", 0);
//            data.put("totalRevenue", 0);
//            monthlyRevenue.add(data);
//        } else {
//            for (Object[] result : results) {
//                Map<String, Object> data = new HashMap<>();
//                data.put("year", result[0]);
//                data.put("month", result[1]);
//                data.put("day", result[2]);
//                data.put("totalRevenue", result[3]);
//                monthlyRevenue.add(data);
//            }
//        }
//
//        return monthlyRevenue;
//    }
    // phương thưc theo ngày nếu không có thì bằng 0
//    public List<Map<String, Object>> getMonthlyRevenueByDay(int year, int month) {
//        List<Object[]> results = orderRepository.findMonthlyRevenueByDay(year, month);
//        List<Map<String, Object>> revenueList = new ArrayList<>();
//
//        // Giả sử tháng có tối đa 31 ngày
//        for (int day = 1; day <= 31; day++) {
//            boolean found = false;
//            for (Object[] result : results) {
//                int resultDay = (int) result[2];
//                if (resultDay == day) {
//                    Map<String, Object> data = new HashMap<>();
//                    data.put("year", result[0]);
//                    data.put("month", result[1]);
//                    data.put("day", result[2]);
//                    data.put("totalRevenue", result[3]);
//                    revenueList.add(data);
//                    found = true;
//                    break;
//                }
//            }
//
//            // Nếu không tìm thấy doanh thu cho ngày này, thêm ngày đó với doanh thu 0
//            if (!found) {
//                Map<String, Object> data = new HashMap<>();
//                data.put("year", year);
//                data.put("month", month);
//                data.put("day", day);
//                data.put("totalRevenue", 0);
//                revenueList.add(data);
//            }
//        }
//
//        return revenueList;
//    }
public List<Map<String, Object>> getMonthlyRevenueByDay(int year, int month) {
    List<Object[]> results = orderRepository.findMonthlyRevenueByDay(year, month);
    List<Map<String, Object>> revenueList = new ArrayList<>();

    // Nếu không có kết quả từ database, tạo một danh sách với doanh thu bằng 0 cho tất cả các ngày trong tháng
    if (results.isEmpty()) {
        for (int day = 1; day <= 31; day++) {  // Giả sử tháng có tối đa 31 ngày
            Map<String, Object> data = new HashMap<>();
            data.put("year", year);
            data.put("month", month);
            data.put("day", day);
            data.put("totalRevenue", 0);  // Không có doanh thu thì doanh thu bằng 0
            revenueList.add(data);
        }
    } else {
        // Nếu có kết quả từ database, kiểm tra và thêm vào revenueList
        for (int day = 1; day <= 31; day++) {
            boolean found = false;
            for (Object[] result : results) {
                int resultDay = (int) result[2];
                if (resultDay == day) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("year", result[0]);
                    data.put("month", result[1]);
                    data.put("day", result[2]);
                    data.put("totalRevenue", result[3]);
                    revenueList.add(data);
                    found = true;
                    break;
                }
            }

            // Nếu không tìm thấy doanh thu cho ngày này, thêm ngày đó với doanh thu 0
            if (!found) {
                Map<String, Object> data = new HashMap<>();
                data.put("year", year);
                data.put("month", month);
                data.put("day", day);
                data.put("totalRevenue", 0);
                revenueList.add(data);
            }
        }
    }

    return revenueList;
}
    // Phương thức lấy doanh thu theo quý (theo tháng trong quý)

//    /**
//     * Phương thức lấy doanh thu theo quý, phân theo tháng trong quý
//     *
//     * @param year: Năm cần lấy doanh thu
//     * @param quarter: Quý (1, 2, 3, 4)
//     * @return danh sách doanh thu phân theo tháng trong quý
//     */
//    public List<Map<String, Object>> getQuarterlyRevenueByMonth(int year, int quarter) {
//        return orderRepository.findQuarterlyRevenueByMonth(year, quarter);
//    }

    // Phương thức lấy doanh thu theo tháng trong quý
    public List<Map<String, Object>> getQuarterlyRevenueByMonth(int year, int quarter) {
        // Lấy kết quả truy vấn
        List<Object[]> result = orderRepository.findQuarterlyRevenueByMonth(year, quarter);

        // Khởi tạo mảng chứa doanh thu cho các tháng trong quý
        int[] months = getMonthsForQuarter(quarter);
        Map<Integer, Double> revenueMap = new HashMap<>();

        // Mặc định doanh thu cho các tháng là 0
        for (int month : months) {
            revenueMap.put(month, 0.0);
        }

        // Cập nhật doanh thu cho các tháng có dữ liệu từ kết quả truy vấn
        for (Object[] row : result) {
            int month = (int) row[1];  // Lấy tháng
            double totalRevenue = (double) row[2];  // Lấy doanh thu
            revenueMap.put(month, totalRevenue);  // Cập nhật doanh thu cho tháng tương ứng
        }

        // Tạo danh sách kết quả để trả về
        List<Map<String, Object>> finalResult = new ArrayList<>();
        for (int month : months) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", month);
            monthData.put("year", year);
            monthData.put("totalRevenue", revenueMap.get(month));  // Doanh thu của tháng, mặc định 0 nếu không có dữ liệu
            finalResult.add(monthData);
        }

        return finalResult;
    }

    // Hàm xác định các tháng trong mỗi quý
    private int[] getMonthsForQuarter(int quarter) {
        switch (quarter) {
            case 1: return new int[]{1, 2, 3};
            case 2: return new int[]{4, 5, 6};
            case 3: return new int[]{7, 8, 9};
            case 4: return new int[]{10, 11, 12};
            default: return new int[]{};
        }
    }


}
