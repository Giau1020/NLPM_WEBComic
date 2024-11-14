package com.example.demo.Service;

import com.example.demo.DTO.SalesStatisticsDTO;
import com.example.demo.repository.SalesDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RevenueService {



    @Autowired
    private SalesDetailRepository salesDetailRepository;



    // Thống kê doanh thu theo sản phẩm
    public List<SalesStatisticsDTO> getSalesStatistics() {
        List<Object[]> results = salesDetailRepository.getSalesStatistics();
        double totalRevenueAll = results.stream()
                .map(result -> (Double) result[3])
                .reduce(0.0, Double::sum);

        List<SalesStatisticsDTO> statistics = new ArrayList<>();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String name = (String) result[1];
            Long totalQuantity = (Long) result[2]; // Đổi thành Long
            double totalRevenue = (Double) result[3];
            double contribution = (totalRevenue / totalRevenueAll) * 100;

            statistics.add(new SalesStatisticsDTO(id, name, totalQuantity, totalRevenue, contribution));
        }
        return statistics;
    }

    // Thống kê doanh thu theo khoảng thời gian
//    public List<SalesStatisticsDTO> getSalesStatisticsByTime(LocalDateTime startDate, LocalDateTime endDate) {
//        List<Object[]> results = salesDetailRepository.getSalesStatisticsByTime(startDate, endDate);
//        double totalRevenueAll = results.stream()
//                .map(result -> (Double) result[3])
//                .reduce(0.0, Double::sum);
//
//        List<SalesStatisticsDTO> statistics = new ArrayList<>();
//        for (Object[] result : results) {
//            Long id = (Long) result[0];
//            String name = (String) result[1];
//            Long totalQuantity = (Long) result[2]; // Đổi thành Long
//            double totalRevenue = (Double) result[3];
//            double contribution = (totalRevenue / totalRevenueAll) * 100;
//
//            statistics.add(new SalesStatisticsDTO(id, name, totalQuantity, totalRevenue, contribution));
//        }
//        return statistics;
//    }


    public List<SalesStatisticsDTO> getSalesStatisticsbyYear(int year) {
        // Gọi phương thức từ repository để lấy dữ liệu
        List<Object[]> results = salesDetailRepository.getSalesStatisticsByYear(year);

        // Tính tổng doanh thu toàn bộ sản phẩm trong năm
        double totalRevenueAll = results.stream()
                .map(result -> (Double) result[3])
                .reduce(0.0, Double::sum);

        // Khởi tạo danh sách thống kê doanh thu theo sản phẩm
        List<SalesStatisticsDTO> statistics = new ArrayList<>();
        for (Object[] result : results) {
            Long id = (Long) result[0];
            String name = (String) result[1];
            Long totalQuantity = (Long) result[2]; // Số lượng bán ra
            double totalRevenue = (Double) result[3]; // Tổng doanh thu
            double contribution = (totalRevenue / totalRevenueAll) * 100; // Tỷ lệ đóng góp

            // Thêm kết quả vào danh sách
            statistics.add(new SalesStatisticsDTO(id, name, totalQuantity, totalRevenue, contribution));
        }

        return statistics;
    }


}
