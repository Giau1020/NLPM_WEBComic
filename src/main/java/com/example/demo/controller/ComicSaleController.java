package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.ComicSaleService;

@RestController
@RequestMapping("/api/comics")
public class ComicSaleController {

    @Autowired
    private ComicSaleService comicSaleService;

    @GetMapping("/top-sellers")
    public List<Object[]> getTopSellingComicsWithDetails() {
        return comicSaleService.getTopSellingComicsWithDetails();
    }
    // Endpoint để lấy danh sách 10 truyện bán chậm nhất
    @GetMapping("/lowest-selling")
    public ResponseEntity<List<Map<String, Object>>> getTop10LowestSellingComics() {
        List<Map<String, Object>> comics = comicSaleService.getTop10LowestSellingComics();
        return ResponseEntity.ok(comics);
    }

}
