package com.example.demo.DTO;



import jakarta.persistence.*;

import java.util.List;

public class ComicDTO {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
    private String name;
    private double price;
    private String url;
    private int sold;
    private Integer quantity;
    private String Weight;
    private String Description;
    private String Pages;
    private String Size;
    private String Publisher;
    private String Summarize;
    private List<Long> authorIds;
    private List<Long> genreIds;

    // Getters and Setters




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPages() {
        return Pages;
    }

    public void setPages(String pages) {
        Pages = pages;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getSummarize() {
        return Summarize;
    }

    public void setSummarize(String summarize) {
        Summarize = summarize;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public List<Long> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Long> genreIds) {
        this.genreIds = genreIds;
    }

    public ComicDTO(String name, double price, String url, int sold, Integer quantity, String weight, String description, String pages, String size, String publisher, String summarize, List<Long> authorIds, List<Long> genreIds) {

        this.name = name;
        this.price = price;
        this.url = url;
        this.sold = sold;
        this.quantity = quantity;
        Weight = weight;
        Description = description;
        Pages = pages;
        Size = size;
        Publisher = publisher;
        Summarize = summarize;
        this.authorIds = authorIds;
        this.genreIds = genreIds;
    }
}
