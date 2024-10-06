package com.example.demo.controller;

import com.example.demo.ServiceImpl.ImgComicServiceImpl;
import com.example.demo.model.ImgComic;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/sng/admin/imgcomic")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class AdminImgComicController {
    private final ImgComicServiceImpl imgComicService;
    @PostMapping("/add")
    public ResponseEntity<?> saveImgComic(@RequestBody ImgComic imgComic) {
        try {
            imgComicService.saveImgComic(imgComic);
            return ResponseEntity.ok().body(imgComic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi thêm truyện: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImgComic> getImgComicById(@PathVariable Long id) {
        Optional<ImgComic> imgComic = imgComicService.findByComicId(id);
        return imgComic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<String> uploadedUrls = imgComicService.uploadImages(files);
        return new ResponseEntity<>(uploadedUrls, HttpStatus.OK);
    }
}
