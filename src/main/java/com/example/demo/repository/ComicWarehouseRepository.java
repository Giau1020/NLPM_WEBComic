package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.ComicWarehouse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComicWarehouseRepository extends JpaRepository<ComicWarehouse, Long> {

    // Đảm bảo câu truy vấn này trả về danh sách các comic
//    @Query(value = "SELECT co.id AS comic_id, co.name AS comic_name, co.publisher, co.quantity, a.name AS author_name " +
//            "FROM Comic co " +
//            "JOIN comic_author ca ON co.id = ca.comic_id " +  // Liên kết với comic_author qua comic_id
//            "JOIN Author a ON ca.author_id = a.id " +  // Liên kết với Author qua author_id
//            "ORDER BY co.id DESC", nativeQuery = true)
    // hien thi 1 tac gia
//    @Query(value = "SELECT co.id AS comic_id, co.name AS comic_name, co.publisher, co.quantity, a.name AS author_name " +
//            "FROM Comic co " +
//            "JOIN comic_author ca ON co.id = ca.comic_id " +  // Liên kết với bảng comic_author qua comic_id
//            "JOIN Author a ON ca.author_id = a.id " +  // Liên kết với bảng Author qua author_id
//            "ORDER BY co.id DESC", nativeQuery = true)
// hiên thị nhiều tác giả hàng tồn kho
    @Query(value = "SELECT co.id AS comic_id, co.name AS comic_name, co.publisher, co.quantity, " +
            "(SELECT STUFF((SELECT ', ' + a.name " +
            " FROM comic_author ca2 " +
            " JOIN Author a ON ca2.author_id = a.id " +
            " WHERE ca2.comic_id = co.id " +
            " FOR XML PATH('')), 1, 2, '')) AS author_names " +
            "FROM Comic co " +
            "GROUP BY co.id, co.name, co.publisher, co.quantity " +
            "ORDER BY co.id DESC", nativeQuery = true)
    List<Object[]> findComicsWithAuthors();

    // low stock
    @Query(value = "SELECT co.id AS comic_id, co.name AS comic_name, co.publisher, co.quantity, " +
            "(SELECT STUFF((SELECT ', ' + a.name " +
            " FROM comic_author ca2 " +
            " JOIN Author a ON ca2.author_id = a.id " +
            " WHERE ca2.comic_id = co.id " +
            " FOR XML PATH('')), 1, 2, '')) AS author_names " +
            "FROM Comic co " +
            "WHERE co.quantity < 7 " +  // Điều kiện để chỉ lấy truyện có số lượng nhỏ hơn 7
            "GROUP BY co.id, co.name, co.publisher, co.quantity " +
            "ORDER BY co.id DESC", nativeQuery = true)
    List<Object[]> findLowStockComics();

}
