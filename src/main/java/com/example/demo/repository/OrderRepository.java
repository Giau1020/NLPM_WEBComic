package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.DTO.DailyRevenueDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    Optional<Order> findById(long id);
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE YEAR(o.orderTime) = :year")
    Double calculateTotalRevenueByYear(int year);

    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.orderTime) = :year")
    Integer countTotalOrdersByYear(int year);

    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi JOIN oi.order o WHERE YEAR(o.orderTime) = :year")
    Integer calculateTotalComicsSoldByYear(int year);

    // Truy vấn các đơn hàng đã thanh toán trong một khoảng thời gian nhất định
    @Query("SELECT o FROM Order o WHERE o.orderTime >= :startDate AND o.orderTime <= :endDate AND o.orderStatus = 'Paid'")
    List<Order> findOrdersByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

////////////////////////////////////////////////////////////////////

    // Tổng số đơn hàng có thời gian
    @Query("SELECT COUNT(o.id) FROM Order o WHERE o.orderTime BETWEEN :startDate AND :endDate")
    Long getTotalOrders(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Tổng doanh thu có thời gian
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.orderTime BETWEEN :startDate AND :endDate")
    Double getTotalRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Tổng số đơn hàng trong toàn bộ hệ thống
    @Query("SELECT COUNT(o.id) FROM Order o")
    Long getTotalOrders();

    // Tổng doanh thu trong toàn bộ hệ thống
    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Double getTotalRevenue();

// truy vấn thống kê theo tháng quý
// Truy vấn doanh thu cho một tháng cụ thể
@Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, SUM(o.totalPrice) AS totalRevenue " +
        "FROM Order o " +
        "WHERE YEAR(o.orderTime) = :year AND MONTH(o.orderTime) = :month " +
        "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime)")
List<Object[]> findMonthlyRevenue(@Param("year") int year, @Param("month") int month);


    // Truy vấn doanh thu cho một quý cụ thể
    @Query("SELECT YEAR(o.orderTime) AS year, QUARTER(o.orderTime) AS quarter, SUM(o.totalPrice) AS totalRevenue " +
            "FROM Order o " +
            "WHERE YEAR(o.orderTime) = :year AND QUARTER(o.orderTime) = :quarter " +
            "GROUP BY YEAR(o.orderTime), QUARTER(o.orderTime)")
    List<Object[]> findQuarterlyRevenue(@Param("year") int year, @Param("quarter") int quarter);

    // Truy vấn doanh thu theo năm và tháng
    // Truy vấn doanh thu theo năm và tháng
    @Query("SELECT MONTH(o.orderTime) AS month, SUM(oi.price * oi.quantity) AS totalRevenue FROM Order o JOIN o.items oi WHERE YEAR(o.orderTime) = :year GROUP BY MONTH(o.orderTime) ORDER BY month ASC")
    List<Object[]> getAllMonthlyRevenue(@Param("year") int year);

    @Query("SELECT QUARTER(o.orderTime) AS quarter, SUM(oi.price * oi.quantity) AS totalRevenue FROM Order o JOIN o.items oi WHERE YEAR(o.orderTime) = :year GROUP BY QUARTER(o.orderTime) ORDER BY quarter ASC")
    List<Object[]> getAllQuarterlyRevenue(@Param("year") int year);



    @Query("SELECT DAY(o.orderTime), SUM(o.totalPrice) FROM Order o " +
            "WHERE YEAR(o.orderTime) = :year AND MONTH(o.orderTime) = :month " +
            "GROUP BY DAY(o.orderTime) ORDER BY DAY(o.orderTime)")
    List<Object[]> findDailyRevenueByMonth(int year, int month);



    // Query để lấy doanh thu theo ngày trong tháng
//    @Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, DAY(o.orderTime) AS day, SUM(o.totalPrice) AS totalRevenue " +
//            "FROM Order o " +
//            "WHERE YEAR(o.orderTime) = :year AND MONTH(o.orderTime) = :month " +
//            "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime), DAY(o.orderTime) " +
//            "ORDER BY DAY(o.orderTime)")
//    List<Object[]> findMonthlyRevenueByDay(@Param("year") int year, @Param("month") int month);

    //// query lấy doanh thu theo ngày và nếu không có thì bằng 0
    @Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, DAY(o.orderTime) AS day, " +
            "IFNULL(SUM(oi.price * oi.quantity), 0) AS totalRevenue " +
            "FROM Order o " +
            "JOIN o.items oi " +
            "WHERE YEAR(o.orderTime) = :year AND MONTH(o.orderTime) = :month " +
            "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime), DAY(o.orderTime) " +
            "ORDER BY DAY(o.orderTime)")
    List<Object[]> findMonthlyRevenueByDay(@Param("year") int year, @Param("month") int month);

// lấy doanh thu theo quý ( theo tháng trong quý)
//@Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, SUM(o.totalPrice) AS totalRevenue " +
//        "FROM Order o " +
//        "WHERE YEAR(o.orderTime) = :year AND QUARTER(o.orderTime) = :quarter " +
//        "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime) " +
//        "ORDER BY MONTH(o.orderTime)")
//List<Map<String, Object>> findQuarterlyRevenueByMonth(@Param("year") int year, @Param("quarter") int quarter);
//
//@Query("SELECT MONTH(o.orderTime) AS month, SUM(o.totalPrice) AS totalRevenue " +
//        "FROM Order o " +
//        "WHERE YEAR(o.orderTime) = :year AND QUARTER(o.orderTime) = :quarter " +
//        "GROUP BY MONTH(o.orderTime) " +
//        "ORDER BY MONTH(o.orderTime)")
//List<Object[]> findQuarterlyRevenueByMonth(@Param("year") int year, @Param("quarter") int quarter);
@Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, SUM(o.totalPrice) AS totalRevenue " +
        "FROM Order o " +
        "WHERE YEAR(o.orderTime) = :year AND QUARTER(o.orderTime) = :quarter " +
        "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime) " +
        "ORDER BY MONTH(o.orderTime)")
List<Object[]> findQuarterlyRevenueByMonth(@Param("year") int year, @Param("quarter") int quarter);

    // Truy vấn doanh thu theo ngày của một tháng


    /////////////////////////////////////////////////////////////

}
