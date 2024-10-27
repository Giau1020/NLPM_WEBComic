package com.example.demo.Service;

import com.example.demo.DTO.ComicDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Comic;
import com.example.demo.model.Genre;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
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

    // Tìm kiếm truyện
    @Query("SELECT DISTINCT c FROM Comic c LEFT JOIN c.authors a LEFT JOIN c.genres g WHERE "
            + "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(a.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
            + "LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Comic> searchByKeyword(@Param("keyword") String keyword);

    //Hàm hiển thị danh sách truyện theo danh sách id
    List<Optional<Comic>> getComicByListId(@PathVariable List<Long> id);

//    Hàm cập nhật truyện tranh
//public Comic updateComic(Long comicId, Comic updatedComic);
    public Comic updateComic(Long comicId, ComicDTO comicDTO);

//    Phương thức xóa truyện
@Modifying
@Query("DELETE FROM CartItem ci WHERE ci.Comic.id IN :comicIds" +" DELETE FROM CartItem ci WHERE ci.comic.id IN :comicIds")
public void deleteComicsByIds(List<Long> comicIds) ;

    public Comic findComicById(Long comicId);


}
