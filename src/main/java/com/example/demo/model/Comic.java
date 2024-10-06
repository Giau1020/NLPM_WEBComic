/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

/**
 *
 * @author ADMIN
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Comic")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comic {

    @Id
    private Long id;
    private String name;
    private double price;
    private String url;
    private int sold;
    private Integer quantity;
    private String Genre;
    private String Weight;
    private String Author;
    private String Description;
    private String Pages;
    private String Size;
    private String Publisher;
    private String Summarize;
    
    // Getters và Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public int getSold() {
//        return sold;
//    }
//
//    public void setSold(int sold) {
//        this.sold = sold;
//    }
//
//
//    public String getGenre() {
//        return Genre;
//    }
//
//    public void setGenre(String Genre) {
//        this.Genre = Genre;
//    }
//
//    public String getWeight() {
//        return Weight;
//    }
//
//    public void setWeight(String Weight) {
//        this.Weight = Weight;
//    }
//
//    public String getAuthor() {
//        return Author;
//    }
//
//    public void setAuthor(String Author) {
//        this.Author = Author;
//    }
//
//    public String getDescription() {
//        return Description;
//    }
//
//    public void setDescription(String Description) {
//        this.Description = Description;
//    }
//
//    public String getPages() {
//        return Pages;
//    }
//
//    public void setPages(String Pages) {
//        this.Pages = Pages;
//    }
//
//    public String getSize() {
//        return Size;
//    }
//
//    public void setSize(String Size) {
//        this.Size = Size;
//    }
//
//    public String getPublisher() {
//        return Publisher;
//    }
//
//    public void setPublisher(String Publisher) {
//        this.Publisher = Publisher;
//    }
//
//    public String getSummarize() {
//        return Summarize;
//    }
//
//    public void setSummarize(String Summarize) {
//        this.Summarize = Summarize;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
    
}

