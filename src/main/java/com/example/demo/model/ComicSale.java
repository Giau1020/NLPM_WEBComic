package com.example.demo.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "order_items")
public class ComicSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "comic_id")
    private Long comicId;

    private int quantity;
    private int price;


    @Transient  // Không phải là cột trong bảng, lấy từ bảng Author
//    private String author;  // Tên tác giả lấy từ bảng Author thông qua bảng comic_author
    private List<String> authors;
    // Getters and Setters


    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return Long return the orderId
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return Long return the comicId
     */
    public Long getComicId() {
        return comicId;
    }

    /**
     * @param comicId the comicId to set
     */
    public void setComicId(Long comicId) {
        this.comicId = comicId;
    }

    /**
     * @return int return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return int return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

}
