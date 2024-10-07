package com.example.demo.controller;

import com.example.demo.ServiceImpl.ComicServiceImpl;
import com.example.demo.model.Comic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/sng/admin")

public class AdminComicController {
    private final ComicServiceImpl comicService;

    public AdminComicController(ComicServiceImpl comicService) {
        this.comicService = comicService;
    }

    //    Hàm thêm vào một truyện mới
    @PostMapping("")
    public ResponseEntity<?> createComic(
            @RequestBody Comic comic
    ){
        try {
            Comic comic1 = comicService.createComic(comic);
            return ResponseEntity.ok().body(comic1);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //    Hàm hiển thị toàn bộ tuyện
    @GetMapping("/comics")
//    public List<Comic> getAllComics(){
//        return comicService.getAllComics();
//    }
    public ResponseEntity<List<Comic>> getAllComics() {
        try {
            List<Comic> comics = comicService.getAllComics();
            return ResponseEntity.ok(comics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //    Hàm hiển thị 1 truyện dựa vào id của truyện
    @GetMapping("/{id}")
    public ResponseEntity<Comic> getComicById(
            @PathVariable Long id
    ){
        Optional<Comic> comic = comicService.getComicById(id);
        return comic.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filePath =comicService.uploadImage(file);
            return ResponseEntity.ok().body(filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
