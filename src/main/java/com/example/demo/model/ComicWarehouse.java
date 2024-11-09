package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Comic")
public class ComicWarehouse {

    @Id
    @Column(name = "id")  // Đảm bảo cột id trong cơ sở dữ liệu là `id`
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "quantity")
    private int quantity;

    @Transient  // Không phải là cột trong bảng, lấy từ bảng Author
//    private String author;  // Tên tác giả lấy từ bảng Author thông qua bảng comic_author
    private List<String> authors;
    // Getters and Setters

//
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
}
