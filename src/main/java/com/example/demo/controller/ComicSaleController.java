package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
