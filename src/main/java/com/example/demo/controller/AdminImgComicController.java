package com.example.demo.controller;

import com.example.demo.ServiceImpl.ImgComicServiceImpl;
import com.example.demo.model.ImgComic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/sng/admin/imgcomic")

@CrossOrigin(origins = "http://127.0.0.1:5501")
public class AdminImgComicController {
    private final ImgComicServiceImpl imgComicService;

    public AdminImgComicController(ImgComicServiceImpl imgComicService) {
        this.imgComicService = imgComicService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveImgComic(@RequestBody ImgComic imgComic) {
        try {
            imgComicService.saveImgComic(imgComic);
            return ResponseEntity.ok().body(imgComic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi thêm truyện: " + e.getMessage());
        }
    }

    @GetMapping("/{comic_id}")
    public ResponseEntity<ImgComic> getImgComicById(@PathVariable Long comic_id) {
        Optional<ImgComic> imgComic = imgComicService.findByComicId(comic_id);
        return imgComic.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<String> uploadedUrls = imgComicService.uploadImages(files);
        return new ResponseEntity<>(uploadedUrls, HttpStatus.OK);
    }

    @PutMapping("/update/{comicId}")
    public ResponseEntity<ImgComic> updateImgComic(
            @PathVariable Long comicId,
            @RequestBody ImgComic newImgComic) {

        ImgComic updatedImgComic = imgComicService.updateImgComic(comicId, newImgComic);
        return ResponseEntity.ok(updatedImgComic);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteImgComicsByComicIds(@RequestBody List<Long> comicIds) {
        int deletedCount = imgComicService.deleteImgComicsByComicIds(comicIds);
        if (deletedCount > 0) {
            return ResponseEntity.ok(deletedCount + " images successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No images found for provided comic IDs.");
        }
    }


}
