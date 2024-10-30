package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ComicSaleRepository;

@Service
public class ComicSaleService {

    @Autowired
    private ComicSaleRepository comicSaleRepository;

    public List<Object[]> getTopSellingComicsWithDetails() {
        return comicSaleRepository.findTopSellingComicsWithDetails();
    }
}
