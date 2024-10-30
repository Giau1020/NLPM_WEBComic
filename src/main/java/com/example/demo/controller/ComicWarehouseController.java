package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    // Retrieve comic by id
    @GetMapping("/{id}")
    public ComicWarehouse getComicById(@PathVariable Long id) {
        return comicWarehouseService.getComicById(id);
    }
}
