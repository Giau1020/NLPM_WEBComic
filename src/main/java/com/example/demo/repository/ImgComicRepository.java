/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.repository;

import com.example.demo.model.ImgComic;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author SangNguyen
 */
@Repository
public interface ImgComicRepository extends JpaRepository<ImgComic, Long> {
    Optional<ImgComic> findByComicId(Long comicId);


    int deleteAllByComicIdIn(List<Long> comicIds);
}
