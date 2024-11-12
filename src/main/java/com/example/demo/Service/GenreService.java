package com.example.demo.Service;

import com.example.demo.model.Genre;
import com.example.demo.repository.ComicRepository;
import com.example.demo.repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Transactional
    public void deleteGenreById(Long id) {
        // Tìm thể loại muốn xóa
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new RuntimeException("Thể loại không tồn tại"));

        // Xóa liên kết với các Comics trước khi xóa thể loại
        genre.getComics().forEach(comic -> comic.getGenres().remove(genre));
        comicRepository.saveAll(genre.getComics());

        // Xóa thể loại
        genreRepository.delete(genre);
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
