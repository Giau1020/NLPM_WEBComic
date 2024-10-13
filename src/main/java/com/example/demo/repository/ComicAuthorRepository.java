package com.example.demo.repository;

import com.example.demo.model.ComicAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicAuthorRepository extends JpaRepository<ComicAuthor, Long> {
}
