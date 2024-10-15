package com.example.demo.ServiceImpl;

import com.example.demo.CORS.ResourceNotFoundException;
import com.example.demo.DTO.ComicDTO;
import com.example.demo.Service.ComicService;
import com.example.demo.model.Author;
import com.example.demo.model.Comic;
import com.example.demo.model.ComicAuthor;
import com.example.demo.model.Genre;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.ComicAuthorRepository;
import com.example.demo.repository.ComicRepository;

import com.example.demo.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service

public class ComicServiceImpl implements ComicService {
    private final ComicRepository comicRepository;

    public ComicServiceImpl(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;

    }

    @Override
    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    @Override
    public Optional<Comic> getComicById(Long id) {
        return comicRepository.findById(id);
    }

    @Override
    public Comic createComic(Comic comic) {
        return null;
    }
//    @Autowired
//    private ComicRepository comicRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public Comic addComic(ComicDTO comicDTO) throws Exception {
        Comic comic = new Comic();
        comic.setName(comicDTO.getName());
        comic.setPrice(comicDTO.getPrice());
        comic.setUrl(comicDTO.getUrl());
        comic.setSold(comicDTO.getSold());
        comic.setQuantity(comicDTO.getQuantity());
        comic.setWeight(comicDTO.getWeight());
        comic.setDescription(comicDTO.getDescription());
        comic.setPages(comicDTO.getPages());
        comic.setSize(comicDTO.getSize());
        comic.setPublisher(comicDTO.getPublisher());
        comic.setSummarize(comicDTO.getSummarize());

        // Liên kết các tác giả
        if (comicDTO.getAuthorIds() != null && !comicDTO.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(comicDTO.getAuthorIds());
            if (authors.size() != comicDTO.getAuthorIds().size()) {
                throw new Exception("Một hoặc nhiều Author IDs không hợp lệ.");
            }
            comic.setAuthors(authors);
        }

        // Liên kết các thể loại
        if (comicDTO.getGenreIds() != null && !comicDTO.getGenreIds().isEmpty()) {
            List<Genre> genres = genreRepository.findAllById(comicDTO.getGenreIds());
            if (genres.size() != comicDTO.getGenreIds().size()) {
                throw new Exception("Một hoặc nhiều Genre IDs không hợp lệ.");
            }
            comic.setGenres(genres);
        }

        return comicRepository.save(comic);
    }


    @Override
    public List<Genre> getComicsByComicId(Long comicId) {
        // Tìm truyện theo ID
        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new ResourceNotFoundException("Comic không tồn tại với ID: " + comicId));

        // Lấy danh sách các thể loại từ truyện
        return comic.getGenres();
    }

    @Override
    public List<Author> getAuthorByComiId(Long comicId) {
        Comic comic = comicRepository.findById(comicId)
                .orElseThrow(() -> new ResourceNotFoundException("Comic không tồn tại với ID: " + comicId));

        // Lấy danh sách các thể loại từ truyện
        return comic.getAuthors();
    }

    // Upload img
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
}


