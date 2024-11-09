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
//    @Query(value = "SELECT TOP 10 c.id, c.name, c.author, c.publisher, SUM(ci.quantity) AS totalQuantity "
//            + "FROM cart_item ci "
//            + "JOIN Comic c ON ci.comic_id = c.id "
//            + "GROUP BY c.id, c.name, c.author, c.publisher "
////            + "ORDER BY totalQuantity DESC", nativeQuery = true)
//    @Query(value = "SELECT TOP 10 c.id AS comic_id, c.name AS comic_name, a.name AS author_name, c.publisher, SUM(ci.quantity) AS totalQuantity " +
//            "FROM cart_item ci " +
//            "JOIN Comic c ON ci.comic_id = c.id " +
//            "JOIN comic_author ac ON c.id = ac.comic_id " +  // Liên kết với bảng AuthorComic qua comic_id
//            "JOIN Author a ON ac.author_id = a.id " +  // Liên kết với bảng Author qua author_id
//            "GROUP BY c.id, c.name, a.name, c.publisher " +
//            "ORDER BY totalQuantity DESC", nativeQuery = true)
    //// chỉnh sửa phần sau này
//    @Query(value = "SELECT TOP 10 c.id AS comic_id, c.name AS comic_name, a.name AS author_name, c.publisher, SUM(ci.quantity) AS totalQuantity " +
//            "FROM cart_item ci " +
//            "JOIN Comic c ON ci.comic_id = c.id " +
//            "JOIN comic_author ac ON c.id = ac.comic_id " +  // Liên kết với bảng comic_author qua comic_id
//            "JOIN Author a ON ac.author_id = a.id " +  // Liên kết với bảng Author qua author_id
//            "GROUP BY c.id, c.name, a.name, c.publisher " +
//            "ORDER BY totalQuantity DESC", nativeQuery = true)

    @Query(value = "SELECT TOP 10 c.id AS comic_id, c.name AS comic_name, " +
            "(SELECT STRING_AGG(a.name, ', ') " +
            " FROM comic_author ac " +
            " JOIN Author a ON ac.author_id = a.id " +
            " WHERE ac.comic_id = c.id) AS author_names, " +
            "c.publisher, SUM(ci.quantity) AS totalQuantity " +
            "FROM cart_item ci " +
            "JOIN Comic c ON ci.comic_id = c.id " +
            "GROUP BY c.id, c.name, c.publisher " +
            "ORDER BY totalQuantity DESC", nativeQuery = true)
    List<Object[]> findTopComicsInCarts();
}
