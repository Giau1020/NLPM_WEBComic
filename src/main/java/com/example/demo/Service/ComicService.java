package com.example.demo.Service;

import com.example.demo.model.Comic;

import java.util.List;
import java.util.Optional;

public interface ComicService {
    //Phương thức lấy danh sách tất cả các truyện trong CSDL
    List<Comic> getAllComics();

    //Phương thức lấy thông tin của 1 truện bằng id
    Optional<Comic> getComicById(Long id);

    //Phương thức tạo 1 comic mới
    Comic createComic(Comic comic);
}
