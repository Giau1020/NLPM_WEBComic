package com.example.demo.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "complaint")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_order")
    private Long orderId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "complaint_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime complaintTime;

    @Column(name = "description_complaint", columnDefinition = "TEXT")
    private String descriptionComplaint;

    @Column(name = "status_complaint")
    private String statusComplaint;

    @Column(name = "image", length = 255, nullable = true)
    private String image;

    @Column(name = "response")
    private String response;
    // Getters and setters
    // Constructors

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
     * @return String return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return String return the descriptionComplaint
     */
    public String getDescriptionComplaint() {
        return descriptionComplaint;
    }

    /**
     * @param descriptionComplaint the descriptionComplaint to set
     */
    public void setDescriptionComplaint(String descriptionComplaint) {
        this.descriptionComplaint = descriptionComplaint;
    }

    /**
     * @return String return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return String return the statusComplaint
     */
    public String getStatusComplaint() {
        return statusComplaint;
    }

    /**
     * @param statusComplaint the statusComplaint to set
     */
    public void setStatusComplaint(String statusComplaint) {
        this.statusComplaint = statusComplaint;
    }

    /**
     * @return LocalDate return the complaintTime
     */
    public LocalDateTime getComplaintTime() {
        return complaintTime;
    }

    /**
     * @param complaintTime the complaintTime to set
     */
    public void setComplaintTime(LocalDateTime complaintTime) {
        this.complaintTime = complaintTime;
    }

    /**
     * @return String return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return String return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
