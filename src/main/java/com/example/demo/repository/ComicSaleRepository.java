package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ComicSale;

@Repository
public interface ComicSaleRepository extends JpaRepository<ComicSale, Long> {

    // @Query("SELECT c.comicId, SUM(c.quantity) AS totalQuantity "
    //         + "FROM ComicSale c "
    //         + "GROUP BY c.comicId "
    //         + "ORDER BY totalQuantity DESC")
    // List<Object[]> findTopSellingComics();
    // @Query(value = "SELECT c.comic_id, co.name, co.Author, co.Publisher, SUM(c.quantity) AS totalQuantity "
    //         + "FROM order_items c "
    //         + "JOIN Comic co ON c.comic_id = co.id "
    //         + "GROUP BY c.comic_id, co.name, co.Author, co.Publisher "
    //         + "ORDER BY totalQuantity DESC", nativeQuery = true)
//    @Query(value = "SELECT TOP 10 c.comic_id, co.name, co.Author, co.Publisher, SUM(c.quantity) AS totalQuantity "
//            + "FROM order_items c "
//            + "JOIN Comic co ON c.comic_id = co.id "
//            + "GROUP BY c.comic_id, co.name, co.Author, co.Publisher "
//            + "ORDER BY totalQuantity DESC", nativeQuery = true)
    // // truyện có lượt bán cao nhất
//    @Query(value = "SELECT TOP 10 c.comic_id, co.name AS comic_name, STRING_AGG(a.name, ', ') AS author_names, co.publisher, SUM(c.quantity) AS total_sales " +
//            "FROM order_items c " +
//            "JOIN Comic co ON c.comic_id = co.id " +
//            "JOIN comic_author ac ON co.id = ac.comic_id " +  // Liên kết với bảng comic_author để lấy author_id
//            "JOIN Author a ON ac.author_id = a.id " +  // Liên kết với bảng Author để lấy tên tác giả
//            "GROUP BY c.comic_id, co.name, co.publisher " +
//            "ORDER BY total_sales DESC", nativeQuery = true)
    @Query(value = "SELECT TOP 10 c.comic_id, co.name AS comic_name, " +
            "(SELECT STRING_AGG(a.name, ', ') " +
            " FROM comic_author ac " +
            " JOIN Author a ON ac.author_id = a.id " +
            " WHERE ac.comic_id = c.comic_id) AS author_names, " +
            "co.publisher, SUM(c.quantity) AS total_sales " +
            "FROM order_items c " +
            "JOIN Comic co ON c.comic_id = co.id " +
            "GROUP BY c.comic_id, co.name, co.publisher " +
            "ORDER BY total_sales DESC", nativeQuery = true)
    List<Object[]> findTopSellingComicsWithDetails();

    // truyện có lượt bán thấp nhất
    @Query(value = "SELECT TOP 10 co.id AS comic_id, co.name AS comic_name, " +
            "STRING_AGG(a.name, ', ') AS author_names, co.publisher, " +
            "COALESCE(SUM(c.quantity), 0) AS total_sales " +
            "FROM Comic co " +
            "LEFT JOIN order_items c ON co.id = c.comic_id " +  // Dùng LEFT JOIN để bao gồm truyện không có lượt bán
            "JOIN comic_author ac ON co.id = ac.comic_id " +
            "JOIN Author a ON ac.author_id = a.id " +
            "GROUP BY co.id, co.name, co.publisher " +
            "ORDER BY total_sales ASC", nativeQuery = true)
    List<Object[]> findTop10LowestSellingComics();




}
