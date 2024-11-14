package com.example.demo.repository;

import com.example.demo.DTO.SalesStatisticsDTO;
import com.example.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;



public interface SalesDetailRepository extends JpaRepository<OrderItem, Long> {

    // Truy vấn doanh thu và số lượng bán ra của mỗi sản phẩm
    @Query("SELECT oi.comic.id, oi.comic.name, SUM(oi.quantity) AS totalQuantity, SUM(oi.quantity * oi.price) AS totalRevenue " +
            "FROM OrderItem oi JOIN oi.comic c " +
            "GROUP BY oi.comic.id, oi.comic.name " +
            "ORDER BY totalRevenue DESC")
    List<Object[]> getSalesStatistics();

    @Query("SELECT YEAR(o.orderTime) AS orderYear, oi.comic.id, oi.comic.name, SUM(oi.quantity) AS totalQuantity, CAST(SUM(oi.quantity * oi.price) AS DOUBLE) AS totalRevenue " +
            "FROM OrderItem oi JOIN oi.comic c JOIN oi.order o " +
            "WHERE YEAR(o.orderTime) = :year " +
            "GROUP BY YEAR(o.orderTime), oi.comic.id, oi.comic.name " +
            "ORDER BY totalRevenue DESC")
    List<Object[]> getSalesStatisticsByYear(int year);









    // Truy vấn doanh thu theo khoảng thời gian
    @Query("SELECT oi.comic.id, oi.comic.name, SUM(oi.quantity) AS totalQuantity, SUM(oi.quantity * oi.price) AS totalRevenue, YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month " +
            "FROM OrderItem oi JOIN oi.order o " +
            "WHERE o.orderTime BETWEEN :startDate AND :endDate " +
            "GROUP BY oi.comic.id, year, month ORDER BY totalRevenue DESC")
    List<Object[]> getSalesStatisticsByTime(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    // truy vấn số lượng bán ra trong 1 năm ( 12 tháng)
    @Query("SELECT oi.comic.id, oi.comic.name, MONTH(o.orderTime) AS month, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi JOIN oi.order o " +
            "WHERE oi.comic.id = :comicId AND YEAR(o.orderTime) = :year " +
            "GROUP BY oi.comic.id, oi.comic.name, MONTH(o.orderTime) " +
            "ORDER BY month ASC")
    List<Object[]> getMonthlySalesStatisticsByYear(@Param("comicId") Long comicId, @Param("year") int year);


    @Query("SELECT oi.comic.id, oi.comic.name, " +
            "CASE " +
            "   WHEN MONTH(o.orderTime) BETWEEN 1 AND 3 THEN 1 " +
            "   WHEN MONTH(o.orderTime) BETWEEN 4 AND 6 THEN 2 " +
            "   WHEN MONTH(o.orderTime) BETWEEN 7 AND 9 THEN 3 " +
            "   WHEN MONTH(o.orderTime) BETWEEN 10 AND 12 THEN 4 " +
            "END AS quarter, " +
            "SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi " +
            "JOIN oi.order o " +
            "WHERE oi.comic.id = :comicId AND YEAR(o.orderTime) = :year " +
            "GROUP BY oi.comic.id, oi.comic.name, quarter " +
            "ORDER BY quarter ASC")
    List<Object[]> getQuarterlySalesStatistics(@Param("comicId") Long comicId, @Param("year") int year);



}
