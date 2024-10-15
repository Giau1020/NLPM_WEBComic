package com.example.demo.request;

import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class ComicAuthorId implements Serializable {

    private Long comic_id;
    private Long author_id;

    public ComicAuthorId(Long comic_id, Long author_id) {
        this.comic_id = comic_id;
        this.author_id = author_id;
    }

    public Long getComic_id() {
        return comic_id;
    }

    public void setComic_id(Long comic_id) {
        this.comic_id = comic_id;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }
}

