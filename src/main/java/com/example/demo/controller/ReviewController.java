/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
@Autowired
    private UserRepository userRepository;
    // API để tạo đánh giá
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest reviewRequest, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Review review = new Review();
        review.setUser(user);
        review.setComicId(reviewRequest.getComicId());
        review.setReviewText(reviewRequest.getReview());
        review.setRating(reviewRequest.getRating());

        reviewRepository.save(review);

        return ResponseEntity.ok("Review created successfully");
    }

    // API để lấy danh sách đánh giá theo comic
 @GetMapping("/comic/{comicId}")
public ResponseEntity<Object> getReviewsByComic(@PathVariable Long comicId) {
    List<Review> reviews = reviewRepository.findByComicId(comicId);

    if (reviews.isEmpty()) {
        // Nếu không có đánh giá, trả về một phản hồi có chứa thông điệp
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sản phẩm chưa có đánh giá");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Sắp xếp đánh giá theo thời gian tạo gần nhất trước (createdAt giảm dần)
    List<ReviewResponseDTO> reviewResponseList = reviews.stream()
        .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt())) // Sắp xếp theo createdAt
        .map(review -> new ReviewResponseDTO(
            review.getUser().getUsername(),  // Lấy tên của người dùng từ đối tượng User
            review.getReviewText(),
            review.getRating(),
            review.getCreatedAt().toString()  // Định dạng thời gian nếu cần
        ))
        .toList();

    return new ResponseEntity<>(reviewResponseList, HttpStatus.OK);
}

// API để trả về thống kê số lượt đánh giá theo từng mức sao
@GetMapping("/comic/{comicId}/ratings-summary")
public ResponseEntity<Map<String, Object>> getRatingsSummary(@PathVariable Long comicId) {
    // Lấy danh sách các đánh giá theo comicId
    List<Review> reviews = reviewRepository.findByComicId(comicId);

    // Khởi tạo các biến đếm số lượt đánh giá cho từng mức sao
    int totalReviews = reviews.size();
    int oneStarCount = 0;
    int twoStarCount = 0;
    int threeStarCount = 0;
    int fourStarCount = 0;
    int fiveStarCount = 0;

    // Duyệt qua danh sách đánh giá để đếm số lượt đánh giá theo từng mức sao
    for (Review review : reviews) {
        switch (review.getRating()) {
            case 1:
                oneStarCount++;
                break;
            case 2:
                twoStarCount++;
                break;
            case 3:
                threeStarCount++;
                break;
            case 4:
                fourStarCount++;
                break;
            case 5:
                fiveStarCount++;
                break;
        }
    }

    // Tạo map để trả về kết quả
    Map<String, Object> response = new HashMap<>();
    response.put("totalReviews", totalReviews);
    response.put("oneStar", oneStarCount);
    response.put("twoStar", twoStarCount);
    response.put("threeStar", threeStarCount);
    response.put("fourStar", fourStarCount);
    response.put("fiveStar", fiveStarCount);

    return new ResponseEntity<>(response, HttpStatus.OK);
}


    // Lớp ReviewResponseDTO được tạo bên trong controller
    public static class ReviewResponseDTO {
        private String userName;
        private String reviewText;
        private int rating;
        private String createdAt;

        // Constructor
        public ReviewResponseDTO(String userName, String reviewText, int rating, String createdAt) {
            this.userName = userName;
            this.reviewText = reviewText;
            this.rating = rating;
            this.createdAt = createdAt;
        }

        // Getters và Setters
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getReviewText() {
            return reviewText;
        }

        public void setReviewText(String reviewText) {
            this.reviewText = reviewText;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    // Lớp request để nhận dữ liệu đánh giá từ client
    public static class ReviewRequest {
        private Long comicId;
        private String review;
        private int rating;

        // Getter và Setter
        public Long getComicId() {
            return comicId;
        }

        public void setComicId(Long comicId) {
            this.comicId = comicId;
        }

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }
    }
}



