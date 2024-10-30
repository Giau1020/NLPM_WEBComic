package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ComicWarehouse;
import com.example.demo.repository.ComicWarehouseRepository;

@Service
public class ComicWarehouseService {

    @Autowired
    private ComicWarehouseRepository comicWarehouseRepository;

    public List<ComicWarehouse> getAllComics() {
        return comicWarehouseRepository.findAll();
    }

    public ComicWarehouse getComicById(Long id) {
        return comicWarehouseRepository.findById(id).orElse(null);
    }
}
