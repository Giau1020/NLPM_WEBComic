/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.Comic;
import com.example.demo.model.Genre;
import com.example.demo.model.ImgComic;
import com.example.demo.model.User;
import com.example.demo.repository.ComicRepository;
import com.example.demo.repository.GenreRepository;
import com.example.demo.repository.ImgComicRepository;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/comics")
public class ComicController {

    @Autowired
    private ComicRepository comicRepository;
     @Autowired
    private ImgComicRepository imgComicRepository;
@Autowired
    private GenreRepository genreRepository;
    // API để lấy 10 truyện có số lượt bán cao nhất
    @GetMapping("/top10byslb")
    public List<Comic> getTop10ComicsBySold() {
        return comicRepository.findTop10ByOrderBySoldDesc();
    }

    // API để lấy 10 truyện có id cao nhất
    @GetMapping("/top10byid")
    public List<Comic> getTop10ComicsById() {
        return comicRepository.findTop10ByOrderByIdDesc();
    }
@GetMapping("/top5byid")
    public List<Comic> getTop5ComicsById() {
        return comicRepository.findTop5ByOrderByIdDesc();
    }
 @GetMapping("/top5similar/{id}")
public ResponseEntity<List<Comic>> getTop5ComicsSimilarTo(@PathVariable Long id) {
    Optional<Comic> comicOptional = comicRepository.findById(id);
    if (comicOptional.isPresent()) {
        String comicName = comicOptional.get().getName();
        // Tách hai từ đầu tiên từ tên truyện
        String[] words = comicName.split(" ");
        String firstTwoWords = (words.length >= 2) ? words[0] + " " + words[1] : comicName;

        // Gọi phương thức để tìm các truyện có hai từ đầu tiên giống nhau
        List<Comic> similarComics = comicRepository.findByFirstTwoWordsAndExcludeId(firstTwoWords, id);
        return ResponseEntity.ok(similarComics);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

    // **API mới** để lấy thông tin chi tiết của một truyện dựa trên ID
   
//      @GetMapping("/{id}")
//    public ResponseEntity<Comic> getComicById(@PathVariable Long id) {
//        Optional<Comic> comic = comicRepository.findById(id);
//        if (comic.isPresent()) {
//            return ResponseEntity.ok(comic.get());
//        } else {
//            return ResponseEntity.notFound().build();  // Trả về 404 nếu không tìm thấy truyện
//        }
//    }
     @GetMapping("/{id}")
    public ResponseEntity<Comic> getComicById(@PathVariable Long id) {
        Optional<Comic> comic = comicRepository.findById(id);
        if (comic.isPresent()) {
            return ResponseEntity.ok(comic.get());
        } else {
            return ResponseEntity.notFound().build();  // Trả về 404 nếu không tìm thấy truyện
        }
    }
    ///////////////////// 
  @GetMapping("/{id}/name")
    public ResponseEntity<String> getComicNameById(@PathVariable Long id) {
        String url = comicRepository.findNameById(id);
        if (url != null) {
            return ResponseEntity.ok(url);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /////////////////////
    @GetMapping("/add-to-cart")
public ResponseEntity<String> addToCart(@RequestParam("comicId") Long comicId, HttpSession session) {
    User loggedInUser = (User) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login to add items to the cart");
    }
    // Logic thêm sản phẩm vào giỏ hàng
    return ResponseEntity.ok("Item added to cart");
}
/////////////////////////////////////////////
 @GetMapping("/{id}/images")
    public ResponseEntity<Map<String, String>> getComicImagesById(@PathVariable Long id) {
        Optional<ImgComic> imgComic = imgComicRepository.findByComicId(id);

        if (imgComic.isPresent()) {
            ImgComic comicImages = imgComic.get();

            // Trả về 5 URL hình ảnh dưới dạng Map
            Map<String, String> imageUrls = new HashMap<>();
            imageUrls.put("url1", comicImages.getUrl1());
            imageUrls.put("url2", comicImages.getUrl2());
            imageUrls.put("url3", comicImages.getUrl3());
            imageUrls.put("url4", comicImages.getUrl4());
            imageUrls.put("url5", comicImages.getUrl5());

            return ResponseEntity.ok(imageUrls);
        } else {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy hình ảnh
        }
    }
    // API để tìm kiếm truyện theo từ khóa
//@GetMapping("/search")
//public ResponseEntity<List<Comic>> searchComics(@RequestParam("query") String query) {
//    List<Comic> comics = comicRepository.findByNameContainingIgnoreCase(query);
//    if (!comics.isEmpty()) {
//        return ResponseEntity.ok(comics);
//    } else {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comics); // Trả về 404 nếu không có kết quả
//    }
//}
      @GetMapping("/search")
    public ResponseEntity<List<Comic>> searchComics(@RequestParam("query") String query) {
        // Tìm kiếm theo cả tên truyện, tác giả, và thể loại
        List<Comic> comics = comicRepository.searchByKeyword(query);
        
        // Kiểm tra kết quả tìm kiếm
        if (!comics.isEmpty()) {
            return ResponseEntity.ok(comics); // Trả về danh sách kết quả
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(comics); // Trả về 404 nếu không có kết quả
        }
    }
  // API để lấy tất cả thể loại
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreRepository.findAll(); // Lấy tất cả thể loại từ cơ sở dữ liệu
        return ResponseEntity.ok(genres); // Trả về danh sách thể loại
    }
    
     // Lấy danh sách truyện theo thể loại
    @GetMapping("/genre/{genreId}")
    public List<Comic> getComicsByGenre(@PathVariable Long genreId) {
        // Tìm thể loại theo genreId
        Genre genre = genreRepository.findById(genreId).orElse(null);
        if (genre != null) {
            // Lấy danh sách truyện thuộc thể loại này
            return comicRepository.findByGenres(genre);
        }
        return null; // Hoặc trả về thông báo lỗi nếu cần
    }
  }
    
