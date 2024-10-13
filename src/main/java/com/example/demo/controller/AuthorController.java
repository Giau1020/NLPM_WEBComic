package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.request.AuthorRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/v1/sng/admin/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @GetMapping("")
    public ResponseEntity<List<Author>> getListAuthor(){
        List<Author> authors = authorRepository.findAll();
        return ResponseEntity.ok().body(authors);
    }

    @PostMapping("/addnew")
    public ResponseEntity<List<Author>> addNewAuthor(@RequestBody AuthorRequest authorRequest) {
        List<Author> savedAuthors = new ArrayList<>();

        for (String name : authorRequest.getNames()) {
            Author author = new Author();
            author.setName(name);
            savedAuthors.add(authorRepository.save(author));
        }

        return ResponseEntity.ok(savedAuthors);
    }
}
