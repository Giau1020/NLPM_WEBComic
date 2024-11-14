package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
     List<OrderItem> findByOrderId(Long orderId);
        List<OrderItem> findByOrder(Order order);

////////////////////////////////////////////////////////////////////
        // truy vấn tháng quý
        @Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, SUM(oi.quantity) AS totalQuantity " +
                "FROM Order o JOIN o.items oi " +
                "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime) ORDER BY year, month")
        List<Object[]> findMonthlyQuantity();
        // Truy vấn số lượng sản phẩm bán ra theo tháng, có tham số year và month
        @Query("SELECT YEAR(o.orderTime) AS year, MONTH(o.orderTime) AS month, SUM(oi.quantity) AS totalQuantity " +
                "FROM Order o JOIN o.items oi " +
                "WHERE YEAR(o.orderTime) = :year AND MONTH(o.orderTime) = :month " +
                "GROUP BY YEAR(o.orderTime), MONTH(o.orderTime)")
        List<Object[]> findMonthlyQuantity(@Param("year") int year, @Param("month") int month);

        // truy vấn số lượng truyện đã bán ra
        // Tổng số lượng truyện bán ra trong toàn bộ hệ thống
        @Query("SELECT SUM(oi.quantity) FROM OrderItem oi")
        Long getTotalComicsSold();



   // Truy vấn tổng số lượng sản phẩm bán ra trong khoảng thời gian
//   @Query("SELECT oi.comic.id, SUM(oi.quantity) FROM OrderItem oi WHERE oi.order.orderTime BETWEEN :startDate AND :endDate GROUP BY oi.comic.id")
//   List<Object[]> findSalesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    // Truy vấn tổng số lượng sản phẩm bán ra trong khoảng thời gian cho một comicId
    @Query("SELECT oi.comic.id, SUM(oi.quantity) FROM OrderItem oi WHERE oi.order.orderTime BETWEEN :startDate AND :endDate AND oi.comic.id = :comicId GROUP BY oi.comic.id")
    List<Object[]> findSalesByDateRangeAndComicId(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate,
                                                  @Param("comicId") Long comicId);


    // Truy vấn số lượng bán ra mỗi ngày trong tháng
    @Query("SELECT oi.comic.id, oi.comic.name, DAY(o.orderTime) AS day, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi JOIN oi.order o " +
            "WHERE oi.comic.id = :comicId AND YEAR(o.orderTime) = :year AND MONTH(o.orderTime) = :month " +
            "GROUP BY oi.comic.id, oi.comic.name, DAY(o.orderTime) " +
            "ORDER BY oi.comic.id, day ASC")
    List<Object[]> getDailySalesStatisticsByComicId(@Param("comicId") Long comicId, @Param("year") int year, @Param("month") int month);


    // Truy vấn tổng số lượng bán cho mỗi truyện
    @Query("SELECT oi.comic.id, oi.comic.name, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi " +
            "GROUP BY oi.comic.id, oi.comic.name " +
            "ORDER BY totalQuantity DESC")
    List<Object[]> getTotalSalesByComic();

    // truy vấn tổng số lượng mỗi tháng trong quý
    @Query("SELECT oi.comic.id, oi.comic.name, MONTH(o.orderTime) AS month, SUM(oi.quantity) AS totalQuantity " +
            "FROM OrderItem oi JOIN oi.order o " +
            "WHERE oi.comic.id = :comicId AND YEAR(o.orderTime) = :year AND QUARTER(o.orderTime) = :quarter " +
            "GROUP BY oi.comic.id, oi.comic.name, MONTH(o.orderTime) " +
            "ORDER BY oi.comic.id, month ASC")
    List<Object[]> getMonthlySalesStatisticsByComicIdAndQuarter(@Param("comicId") Long comicId,
                                                                @Param("year") int year,
                                                                @Param("quarter") int quarter);

    ////////////////////////////////////////////////////////////////////////////

}
