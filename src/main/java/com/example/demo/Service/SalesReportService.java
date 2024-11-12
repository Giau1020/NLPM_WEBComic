package com.example.demo.Service;

import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.SalesDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class SalesReportService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private SalesDetailRepository salesDetailRepository;

    // Thống kê số lượng bán trong tháng cho một sản phẩm
    public List<Map<String, Object>> getMonthlySales(int month, int year, Long comicId) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusDays(1);

        List<Object[]> salesData = orderItemRepository.findSalesByDateRangeAndComicId(startDate, endDate, comicId);
        return mapSalesDataToResult(salesData);
    }

    // Thống kê số lượng bán trong quý cho một sản phẩm
    public List<Map<String, Object>> getQuarterlySales(int quarter, int year, Long comicId) {
        int startMonth = (quarter - 1) * 3 + 1;
        int endMonth = quarter * 3;

        LocalDateTime startDate = LocalDateTime.of(year, startMonth, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, endMonth, 1, 0, 0).plusMonths(1).minusDays(1);

        List<Object[]> salesData = orderItemRepository.findSalesByDateRangeAndComicId(startDate, endDate, comicId);
        return mapSalesDataToResult(salesData);
    }

    // Thống kê số lượng bán trong năm cho một sản phẩm
    public List<Map<String, Object>> getYearlySales(int year, Long comicId) {
        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59);

        List<Object[]> salesData = orderItemRepository.findSalesByDateRangeAndComicId(startDate, endDate, comicId);
        return mapSalesDataToResult(salesData);
    }

    // Chuyển đổi dữ liệu từ database sang kết quả dễ đọc
    private List<Map<String, Object>> mapSalesDataToResult(List<Object[]> salesData) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] data : salesData) {
            Map<String, Object> record = new HashMap<>();
            record.put("comicId", data[0]);
            record.put("quantitySold", data[1]);
            result.add(record);
        }
        return result;
    }

    // Lấy số lượng bán theo ngày của một comicId trong tháng
    public List<Map<String, Object>> getDailySalesStatisticsByComicId(Long comicId, int year, int month) {
        List<Object[]> results = orderItemRepository.getDailySalesStatisticsByComicId(comicId, year, month);

        // Chuyển đổi dữ liệu từ Object[] sang Map để dễ sử dụng trong Controller hoặc Frontend
        List<Map<String, Object>> dailySales = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> sale = new HashMap<>();
            sale.put("comicId", result[0]);
            sale.put("comicName", result[1]);
            sale.put("day", result[2]);
            sale.put("totalQuantity", result[3]);
            dailySales.add(sale);
        }

        return dailySales;
    }
    // Lấy tổng số lượng bán cho mỗi truyện
    public List<Map<String, Object>> getTotalSalesByComic() {
        List<Object[]> results = orderItemRepository.getTotalSalesByComic();

        // Chuyển đổi dữ liệu từ Object[] sang Map để dễ sử dụng trong Controller hoặc Frontend
        List<Map<String, Object>> totalSales = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> sale = new HashMap<>();
            sale.put("comicId", result[0]);
            sale.put("comicName", result[1]);
            sale.put("totalQuantity", result[2]);
            totalSales.add(sale);
        }

        return totalSales;
    }

//    public List<Object[]> getMonthlySalesStatistics(Long comicId, int year, int quarter) {
//        return orderItemRepository.getMonthlySalesStatisticsByComicIdAndQuarter(comicId, year, quarter);
//    }
//public List<Map<String, Object>> getMonthlySalesStatistics(Long comicId, int year, int quarter) {
//    // Lấy dữ liệu từ repository
//    List<Object[]> results = orderItemRepository.getMonthlySalesStatisticsByComicIdAndQuarter(comicId, year, quarter);
//
//    // Tạo danh sách để chứa kết quả dưới dạng Map
//    List<Map<String, Object>> totalSales = new ArrayList<>();
//
//    // Duyệt qua từng kết quả Object[] và chuyển đổi thành Map
//    for (Object[] result : results) {
//        Map<String, Object> sale = new HashMap<>();
//        sale.put("comicId", result[0]);
//        sale.put("comicName", result[1]);
//        sale.put("totalQuantity", result[2]);
//
//        // Thêm mỗi bản ghi vào danh sách
//        totalSales.add(sale);
//    }
//
//    return totalSales;  // Trả về danh sách Map
//}
// Lấy doanh thu bán theo tháng trong quý cho một truyện
public List<Map<String, Object>> getMonthlySalesStatistics(Long comicId, int year, int quarter) {
    // Gọi phương thức từ repository để lấy dữ liệu
    List<Object[]> results = orderItemRepository.getMonthlySalesStatisticsByComicIdAndQuarter(comicId, year, quarter);

    // Tạo danh sách các map để chứa dữ liệu trả về
    List<Map<String, Object>> totalSales = new ArrayList<>();

    for (Object[] result : results) {
        Map<String, Object> sale = new HashMap<>();
        sale.put("comicId", result[0]);
        sale.put("comicName", result[1]);
        sale.put("month", result[2]);
        sale.put("totalQuantity", result[3]);

        totalSales.add(sale);
    }

    return totalSales;
}


    // Phương thức lấy số lượng bán hàng theo tháng cho một comic trong một năm
    public List<Map<String, Object>> getMonthlySalesStatistics(Long comicId, int year) {
        // Gọi phương thức từ repository để lấy dữ liệu
        List<Object[]> results = salesDetailRepository.getMonthlySalesStatisticsByYear(comicId, year);

        // Tạo danh sách các map để chứa dữ liệu trả về
        List<Map<String, Object>> totalSales = new ArrayList<>();

        // Lấy tên truyện từ kết quả truy vấn
        String comicName = results.isEmpty() ? "" : results.get(0)[1].toString();

        // Mảng tháng trong năm từ 1 đến 12
        int[] months = new int[12];
        for (int i = 0; i < 12; i++) {
            months[i] = i + 1;
        }

        // Duyệt qua từng tháng (1 đến 12) và thêm dữ liệu vào danh sách
        for (int month : months) {
            // Tạo một Map mới cho mỗi tháng
            Map<String, Object> sale = new HashMap<>();
            sale.put("month", month);
            sale.put("comicId", comicId); // ID của sản phẩm
            sale.put("comicName", comicName);  // Cập nhật tên truyện cho mọi tháng

            // Kiểm tra nếu tháng này có dữ liệu
            boolean monthFound = false;
            for (Object[] result : results) {
                if (Integer.parseInt(result[2].toString()) == month) {
                    sale.put("totalQuantity", result[3]); // Số lượng bán
                    monthFound = true;
                    break;
                }
            }

            // Nếu không có dữ liệu cho tháng này, gán số lượng bán là 0
            if (!monthFound) {
                sale.put("totalQuantity", 0);
            }

            // Thêm vào danh sách kết quả
            totalSales.add(sale);
        }

        // Trả về danh sách số lượng bán cho từng tháng trong năm
        return totalSales;
    }


//  Phương thức lấy số lượng bán hàng theo quý cho một comic trong một năm
    public List<Map<String, Object>> getQuarterlySalesStatistics(Long comicId, int year) {
        // Gọi phương thức từ repository để lấy dữ liệu
        List<Object[]> results = salesDetailRepository.getQuarterlySalesStatistics(comicId, year);

        // Tạo danh sách các map để chứa dữ liệu trả về
        List<Map<String, Object>> totalSales = new ArrayList<>();

        // Lấy tên truyện từ kết quả truy vấn
        String comicName = results.isEmpty() ? "" : results.get(0)[1].toString();

        // Quý trong năm
        int[] quarters = {1, 2, 3, 4};

        // Duyệt qua từng quý (1 đến 4) và thêm dữ liệu vào danh sách
        for (int quarter : quarters) {
            // Tạo một Map mới cho mỗi quý
            Map<String, Object> sale = new HashMap<>();
            sale.put("quarter", "Q" + quarter);
            sale.put("comicId", comicId); // ID của sản phẩm
            sale.put("comicName", comicName);  // Cập nhật tên truyện cho mọi quý

            // Kiểm tra nếu quý này có dữ liệu
            boolean quarterFound = false;
            for (Object[] result : results) {
                if (Integer.parseInt(result[2].toString()) == quarter) {
                    sale.put("totalQuantity", result[3]); // Số lượng bán
                    quarterFound = true;
                    break;
                }
            }

            // Nếu không có dữ liệu cho quý này, gán số lượng bán là 0
            if (!quarterFound) {
                sale.put("totalQuantity", 0);
            }

            // Thêm vào danh sách kết quả
            totalSales.add(sale);
        }

        // Trả về danh sách số lượng bán cho từng quý trong năm
        return totalSales;
    }
}
