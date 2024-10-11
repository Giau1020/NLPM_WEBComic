package com.example.demo.Service;

import com.example.demo.model.Author;
import com.example.demo.model.Comic;
import com.example.demo.model.Genre;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ComicService {
    //Phương thức lấy danh sách tất cả các truyện trong CSDL
    List<Comic> getAllComics();

    //Phương thức lấy thông tin của 1 truện bằng id
    Optional<Comic> getComicById(Long id);

    //Phương thức tạo 1 comic mới
    Comic createComic(Comic comic);

    //Phương thức lấy danh sách thể loại bằng ID comic
    public List<Genre> getComicsByComicId(@PathVariable Long ComicId);

    //Phương thức lấy danh sách tác giả bằng ID comic
    List<Author> getAuthorByComiId(@PathVariable Long ComicId);

}
