package com.example.demo.controller;

import com.example.demo.model.Genre;
import com.example.demo.repository.GenreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/sng/admin/genres")
public class GenreController {
    private final GenreRepository genreRepository;


    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

//    hàm lấy danh sách thể loại
    @RequestMapping("")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        
        return ResponseEntity.ok(genres);
    }
}
