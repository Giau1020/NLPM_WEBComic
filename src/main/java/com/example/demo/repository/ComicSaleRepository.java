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
    @Query(value = "SELECT TOP 10 c.comic_id, co.name, co.Author, co.Publisher, SUM(c.quantity) AS totalQuantity "
            + "FROM order_items c "
            + "JOIN Comic co ON c.comic_id = co.id "
            + "GROUP BY c.comic_id, co.name, co.Author, co.Publisher "
            + "ORDER BY totalQuantity DESC", nativeQuery = true)
    List<Object[]> findTopSellingComicsWithDetails();
}
