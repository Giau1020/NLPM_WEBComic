package com.example.demo.controller;

import com.example.demo.DTO.GenreDTO;
import com.example.demo.Service.GenreService;
import com.example.demo.model.Genre;
import com.example.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/v1/sng/admin/genres")
public class GenreController {
    private final GenreRepository genreRepository;


    public GenreController(GenreRepository genreRepository, GenreService genreService) {
        this.genreRepository = genreRepository;
        this.genreService = genreService;
    }

//    hàm lấy danh sách thể loại
    @RequestMapping("")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        
        return ResponseEntity.ok(genres);
    }

    @Autowired
    private final GenreService genreService;
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long id) {
        try {
            genreService.deleteGenreById(id);
            return ResponseEntity.ok("Thể loại đã được xóa thành công.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xóa thể loại: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDTO genreDTO) {
        try {
            Genre genre = new Genre();
            genre.setName(genreDTO.getName());

            // Thêm thể loại mới vào cơ sở dữ liệu
            Genre savedGenre = genreService.addGenre(genre);
            return new ResponseEntity<>(savedGenre, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
