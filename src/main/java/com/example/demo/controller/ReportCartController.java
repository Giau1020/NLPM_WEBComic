package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.ReportCartService;

@RestController
@RequestMapping("/api/carts")
public class ReportCartController {

    @Autowired
    private ReportCartService cartService;

    @GetMapping("/top-comics")
    public ResponseEntity<List<Object[]>> getTopComicsInCarts() {
        List<Object[]> topComics = cartService.getTopComicsInCarts();
        return new ResponseEntity<>(topComics, HttpStatus.OK);
    }
}
