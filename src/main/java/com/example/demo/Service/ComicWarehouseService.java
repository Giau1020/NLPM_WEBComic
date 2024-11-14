package com.example.demo.Service;

import java.util.*;

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

//    // Đảm bảo rằng phương thức này trả về dữ liệu từ Repository
//    public List<ComicWarehouse> getComicsWithAuthor() {
//        List<Object[]> results = comicWarehouseRepository.findComicsWithAuthor();
//        List<ComicWarehouse> comicsWithAuthor = new ArrayList<>();
//
//        for (Object[] result : results) {
//            ComicWarehouse comic = new ComicWarehouse();
//            comic.setId((Long) result[0]);  // comic_id
//            comic.setName((String) result[1]);  // comic_name
//            comic.setPublisher((String) result[2]);
//            comic.setQuantity((Integer) result[3]);
//            comic.setAuthor((String) result[4]);  // author_name
//
//            comicsWithAuthor.add(comic);
//        }
//
//        return comicsWithAuthor;
//    }

    public List<ComicWarehouse> getComicsWithAuthors() {
        List<Object[]> results = comicWarehouseRepository.findComicsWithAuthors();
        List<ComicWarehouse> comicsWithAuthors = new ArrayList<>();

        for (Object[] result : results) {
            ComicWarehouse comic = new ComicWarehouse();
            comic.setId((Long) result[0]);  // comic_id
            comic.setName((String) result[1]);  // comic_name
            comic.setPublisher((String) result[2]);
            comic.setQuantity((Integer) result[3]);

            // Chuyển chuỗi tên tác giả thành danh sách
            String authorNames = (String) result[4];
            List<String> authors = Arrays.asList(authorNames.split(", "));
            comic.setAuthors(authors);

            comicsWithAuthors.add(comic);
        }

        return comicsWithAuthors;
    }
    // low stock
    public List<Map<String, Object>> getLowStockComics() {
        List<Object[]> results = comicWarehouseRepository.findLowStockComics();
        List<Map<String, Object>> comics = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> comicData = new HashMap<>();
            comicData.put("comic_id", row[0]);
            comicData.put("comic_name", row[1]);
            comicData.put("publisher", row[2]);
            comicData.put("quantity", row[3]);
            comicData.put("author_names", row[4]);
            comics.add(comicData);
        }

        return comics;
    }

}
