package com.example.demo.request;

import com.example.demo.model.Comic;

import java.util.List;

public class ComicAuthorRequest {
    private Comic comic;
    private List<Long> authorIds;

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }
// Getters và Setters
}
