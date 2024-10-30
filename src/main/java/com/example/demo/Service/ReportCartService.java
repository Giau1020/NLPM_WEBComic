package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ReportCartRepository;

@Service
public class ReportCartService {

    @Autowired
    private ReportCartRepository reportCartRepository;

    public List<Object[]> getTopComicsInCarts() {
        return reportCartRepository.findTopComicsInCarts();
    }
}
