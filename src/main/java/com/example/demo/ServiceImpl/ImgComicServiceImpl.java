package com.example.demo.ServiceImpl;

import com.example.demo.Service.ImgComicService;
import com.example.demo.model.ImgComic;
import com.example.demo.repository.ImgComicRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ImgComicServiceImpl implements ImgComicService {
    private final ImgComicRepository imgComicRepository;

    public ImgComicServiceImpl(ImgComicRepository imgComicRepository) {
        this.imgComicRepository = imgComicRepository;
    }

    private static final String UPLOAD_DIR = "C:/Users/NgocG/Documents/NLPM_WebComic_SNG/NLPM_WEBComic/src/main/resources/static/images/";
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("File is empty!");
        }

        // Tạo thư mục nếu chưa tồn tại
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Tạo đường dẫn cho file
        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        Path path = Paths.get(filePath);

        // Lưu tệp
        Files.write(path, file.getBytes());
        return "../images/" + file.getOriginalFilename(); // Trả về đường dẫn tệp đã tải lên
    }


@Override
    public ImgComic saveImgComic(ImgComic imgComic) {
        return imgComicRepository.save(imgComic);
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> files) {
        List<String> uploadedUrls = new ArrayList<>();

        // Kiểm tra số lượng tệp tải lên
        if (files.size() != 5) {
            throw new IllegalArgumentException("Bạn chỉ có thể tải lên tối đa 5 tệp.");

        }

        for (MultipartFile file : files) {
            try {
                // Gọi hàm uploadImage để tải từng tệp lên
                String fileUrl = uploadImage(file);
                uploadedUrls.add(fileUrl); // Thêm URL vào danh sách
            } catch (IOException e) {
                // Xử lý ngoại lệ nếu có
                System.err.println("Lỗi khi tải lên tệp: " + e.getMessage());
                // Bạn có thể ném lại ngoại lệ hoặc xử lý theo cách khác tùy thuộc vào yêu cầu
            }
        }
        if (uploadedUrls.isEmpty()) {
            throw new RuntimeException("Không thể tải lên bất kỳ hình ảnh nào.");
        }

        return uploadedUrls;
    }

    @Override
    public Optional<ImgComic> findByComicId(Long comicId) {
        return imgComicRepository.findByComicId(comicId);
    }


//    Cập nhật imgComic

@Override
    public ImgComic updateImgComic(Long comicId, ImgComic newImgComic) {
        // Tìm ImgComic theo comicId
        Optional<ImgComic> existingImgComic = imgComicRepository.findByComicId(comicId);

        if (existingImgComic.isPresent()) {
            ImgComic imgComic = existingImgComic.get();

            // Cập nhật các trường ảnh
            imgComic.setUrl1(newImgComic.getUrl1());
            imgComic.setUrl2(newImgComic.getUrl2());
            imgComic.setUrl3(newImgComic.getUrl3());
            imgComic.setUrl4(newImgComic.getUrl4());
            imgComic.setUrl5(newImgComic.getUrl5());

            // Lưu thay đổi vào database
            return imgComicRepository.save(imgComic);
        } else {
            // Nếu không tìm thấy, có thể xử lý ngoại lệ hoặc tạo mới (nếu cần)
            throw new EntityNotFoundException("ImgComic with comicId " + comicId + " not found.");
        }
    }


    @Override
    public int deleteImgComicsByComicIds(List<Long> comicIds) {
        return imgComicRepository.deleteAllByComicIdIn(comicIds);
    }

    // In ImgComicServiceImpl class





}
