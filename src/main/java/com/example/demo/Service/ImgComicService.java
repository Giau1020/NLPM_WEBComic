package com.example.demo.Service;

import com.example.demo.model.Comic;
import com.example.demo.model.ImgComic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImgComicService {
    //Hàm lưu img Comic vào CSDL
   ImgComic saveImgComic(ImgComic imgComic);

    //Hàm upload comic lên server (tự lưu vào folder img)
    List<String> uploadImages(List<MultipartFile> files);

    //Hàm lấy Hình ảnh comic bằng id
    Optional<ImgComic> findByComicId(Long comicId);

    ImgComic updateImgComic(Long comicId, ImgComic newImgComic);
    // In ImgComicServiceImpl class

    //ImgComic deleteAllByComicIdIn(Long comicId);

    int deleteImgComicsByComicIds(List<Long> comicIds);


}
