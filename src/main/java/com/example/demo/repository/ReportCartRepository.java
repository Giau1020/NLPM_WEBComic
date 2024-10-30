package com.example.demo.repository;

// import java.util.List;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import com.example.demo.model.ReportCart;
// public interface ReportCartRepository extends JpaRepository<ReportCart, Long> {
//     @Query("SELECT rc.comicId, SUM(rc.quantity) AS totalQuantity "
//             + "FROM ReportCart rc "
//             + "GROUP BY rc.comicId "
//             + "ORDER BY totalQuantity DESC")
//     List<Object[]> findTopComics();
//     @Query("SELECT c.name, c.author, c.publisher FROM Comic c WHERE c.id = :comicId")
//     Object[] findComicDetailsById(Long comicId);
// }
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.ReportCart;

public interface ReportCartRepository extends JpaRepository<ReportCart, Long> {

    // @Query(value = "SELECT TOP 10 comic_id, SUM(quantity) AS totalQuantity "
    //         + "FROM cart_item "
    //         + "GROUP BY comic_id "
    //         + "ORDER BY totalQuantity DESC", nativeQuery = true)
    @Query(value = "SELECT TOP 10 c.id, c.name, c.author, c.publisher, SUM(ci.quantity) AS totalQuantity "
            + "FROM cart_item ci "
            + "JOIN Comic c ON ci.comic_id = c.id "
            + "GROUP BY c.id, c.name, c.author, c.publisher "
            + "ORDER BY totalQuantity DESC", nativeQuery = true)

    List<Object[]> findTopComicsInCarts();
}
