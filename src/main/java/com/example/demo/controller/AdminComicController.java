package com.example.demo.controller;

import com.example.demo.DTO.ComicDTO;
import com.example.demo.Service.ComicService;
import com.example.demo.ServiceImpl.ComicServiceImpl;
import com.example.demo.model.Author;
import com.example.demo.model.Comic;
import com.example.demo.model.ComicAuthor;
import com.example.demo.model.Genre;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.ComicAuthorRepository;
import com.example.demo.repository.ComicRepository;
import com.example.demo.request.ComicAuthorId;
import com.example.demo.request.ComicAuthorRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/sng/admin")

public class AdminComicController {
    private final ComicServiceImpl comicService;

    public AdminComicController(ComicServiceImpl comicService, ComicAuthorRepository comicAuthorRepository, AuthorRepository authorRepository) {
        this.comicService = comicService;
        this.comicAuthorRepository = comicAuthorRepository;
        this.authorRepository = authorRepository;
    }
private final ComicAuthorRepository comicAuthorRepository;
    private final AuthorRepository authorRepository;
    //    Hàm thêm vào một truyện mới

    @PostMapping("/addNewComic")
    public ResponseEntity<?> addComic(@RequestBody ComicDTO comicDTO) {
        try {
            System.out.println("Dữ liệu nhận được: " + comicDTO);
            System.out.println("Dữ liệu nhận được: " + comicDTO.getSummarize());
            Comic savedComic = comicService.addComic(comicDTO);
            return ResponseEntity.ok(savedComic);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //    Hàm hiển thị toàn bộ tuyện
    @GetMapping("/comics")
//    public List<Comic> getAllComics(){
//        return comicService.getAllComics();
//    }
    public ResponseEntity<List<Comic>> getAllComics() {
        try {
            List<Comic> comics = comicService.getAllComics();
            return ResponseEntity.ok(comics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //    Hàm hiển thị 1 truyện dựa vào id của truyện
    @GetMapping("/{id}")
    public ResponseEntity<Comic> getComicById(
            @PathVariable Long id
    ){
        Optional<Comic> comic = comicService.getComicById(id);
        return comic.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String filePath =comicService.uploadImage(file);
            return ResponseEntity.ok().body(filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

//    Hàm lấy thể loại bằng comic ID
    @GetMapping("/{comicId}/genres")
    public ResponseEntity<List<Genre>> getGenresByComicId(@PathVariable Long comicId) {
        List<Genre> genres = comicService.getComicsByComicId(comicId);
        return ResponseEntity.ok(genres);
    }

//    Hàm lấy danh sách tác giả bằng comic ID
    @GetMapping("/{comicId}/authors")
    public ResponseEntity<List<Author>> getAuthorByComicId(@PathVariable Long comicId){
        List<Author> authors = comicService.getAuthorByComiId((comicId));
        return ResponseEntity.ok(authors);
    }

   //private ComicService comicService1;
    @GetMapping("/comic/search")
    public ResponseEntity<List<Comic>> searchComics(@RequestParam("query") String query) {
        // Tìm kiếm theo cả tên truyện, tác giả, và thể loại
        List<Comic> comics = comicService.searchByKeyword(query);

        // Kiểm tra kết quả tìm kiếm
        if (!comics.isEmpty()) {
            return ResponseEntity.ok(comics); // Trả về danh sách kết quả
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comics); // Trả về 404 nếu không có kết quả
        }
    }



    //Hiển thị danh sách truyện theo id
    @GetMapping("/comic/list_comic_search")
    public List<Optional<Comic>> getComicsByListId(List<Long> comic_id){
        return comicService.getComicByListId(comic_id);
    }
}
