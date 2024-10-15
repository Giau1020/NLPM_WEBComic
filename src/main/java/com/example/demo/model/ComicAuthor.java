package com.example.demo.model;

import com.example.demo.request.ComicAuthorId;
import jakarta.persistence.*;

@Entity
@Table(name = "comic_author")
public class ComicAuthor {


    @EmbeddedId
    private ComicAuthorId id;

    public ComicAuthorId getId() {
        return id;
    }

    public void setId(ComicAuthorId id) {
        this.id = id;
    }
}
