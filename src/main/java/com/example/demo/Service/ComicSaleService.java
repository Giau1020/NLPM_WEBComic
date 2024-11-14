package com.example.demo.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // truyện có lượt bán thấp nhahats
    public List<Map<String, Object>> getTop10LowestSellingComics() {
        List<Object[]> results = comicSaleRepository.findTop10LowestSellingComics();
        List<Map<String, Object>> comics = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> comicData = new HashMap<>();
            comicData.put("comic_id", row[0]);
            comicData.put("comic_name", row[1]);
            comicData.put("author_names", row[2]);
            comicData.put("publisher", row[3]);
            comicData.put("total_sales", row[4]);
            comics.add(comicData);
        }

        return comics;
    }
}
