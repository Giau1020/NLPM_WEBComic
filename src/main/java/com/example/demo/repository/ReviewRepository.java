/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.repository;

import com.example.demo.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Tìm các đánh giá dựa trên comicId
    List<Review> findByComicId(Long comicId);
  @Query("SELECT COUNT(r) FROM Review r WHERE r.comicId = :comicId")
    Long countByComicId(@Param("comicId") Long comicId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.comicId = :comicId")
    Double findAverageRatingByComicId(@Param("comicId") Long comicId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.comicId = :comicId AND r.rating = :rating")
    Long countByComicIdAndRating(@Param("comicId") Long comicId, @Param("rating") int rating);
}
