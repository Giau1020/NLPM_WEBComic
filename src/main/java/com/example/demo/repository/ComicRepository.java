/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.repository;

/**
 *
 * @author ADMIN
 */
import com.example.demo.model.Comic;
import com.example.demo.model.Genre;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {

    // Phương thức lấy 10 truyện có số lượt bán cao nhất
    @Query(value = "SELECT TOP 10 * FROM Comic ORDER BY sold DESC", nativeQuery = true)
    List<Comic> findTop10ByOrderBySoldDesc();

    // Phương thức lấy 10 truyện có id cao nhất
    @Query(value = "SELECT TOP 10 * FROM Comic ORDER BY id DESC", nativeQuery = true)
    List<Comic> findTop10ByOrderByIdDesc();

    // Phương thức lấy 5 truyện có id cao nhất
    @Query(value = "SELECT TOP 5 * FROM Comic ORDER BY id DESC", nativeQuery = true)
    List<Comic> findTop5ByOrderByIdDesc();

    // Phương thức lấy tên truyện theo id
    @Query("SELECT c.name FROM Comic c WHERE c.id = :id")
    String findNameById(@Param("id") Long id);

    // Tìm các truyện có hai chữ đầu trong tên giống với hai chữ đầu của truyện chính và loại trừ truyện có ID đã cho
    @Query("SELECT c FROM Comic c WHERE LOWER(c.name) LIKE LOWER(CONCAT(:firstTwoWords, '%')) AND c.id <> :id ORDER BY c.id DESC")
List<Comic> findByFirstTwoWordsAndExcludeId(@Param("firstTwoWords") String firstTwoWords, @Param("id") Long id);
//     List<Comic> findByNameContainingIgnoreCase(String name);

    public List<Comic> findByGenres(Genre genre);


// Tìm kiếm theo tất cả các tiêu chí (tên truyện, tác giả, thể loại)
    @Query("SELECT DISTINCT c FROM Comic c LEFT JOIN c.authors a LEFT JOIN c.genres g WHERE "
            + "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Comic> searchByKeyword(@Param("keyword") String keyword);


}




