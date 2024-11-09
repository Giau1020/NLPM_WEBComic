package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.ComicWarehouseService;
import com.example.demo.model.ComicWarehouse;

@RestController
@RequestMapping("/api/comics")
public class ComicWarehouseController {

    @Autowired
    private ComicWarehouseService comicWarehouseService;

    // Retrieve all comics
    @GetMapping
    public List<ComicWarehouse> getAllComics() {
        return comicWarehouseService.getAllComics();
    }
//
//    // Retrieve comic by id
//    @GetMapping("/{id}")
//    public ComicWarehouse getComicById(@PathVariable Long id) {
//        return comicWarehouseService.getComicById(id);
//    }

//    @GetMapping("/with-author")
//    public List<ComicWarehouse> getComicsWithAuthor() {
//        return comicWarehouseService.getComicsWithAuthor();
//    }
//    @GetMapping("/with-author")
//    public List<ComicWarehouse> getComicsWithAuthor() {
//        return comicWarehouseService.getComicsWithAuthor();
//    }
   // Lấy danh sách các truyện cùng với tên tác giả (có thể là nhiều tác giả)
    @GetMapping("/with-authors")
    public ResponseEntity<List<ComicWarehouse>> getComicsWithAuthors() {
        List<ComicWarehouse> comicsWithAuthors = comicWarehouseService.getComicsWithAuthors();
        return ResponseEntity.ok(comicsWithAuthors);
    }

    // Retrieve comic by id
    @GetMapping("/{id}")
    public ComicWarehouse getComicById(@PathVariable Long id) {
        return comicWarehouseService.getComicById(id);
    }
    // Endpoint để lấy danh sách truyện có số lượng nhỏ hơn 7
    @GetMapping("/low-stock")
    public ResponseEntity<List<Map<String, Object>>> getLowStockComics() {
        List<Map<String, Object>> comics = comicWarehouseService.getLowStockComics();
        return ResponseEntity.ok(comics);
    }

}
