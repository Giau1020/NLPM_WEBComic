package com.example.demo.controller;

import com.example.demo.DTO.UpdatePassDTO;
import com.example.demo.model.Order;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/sng/admin/users")
public class AdminUserController {
    @Autowired
    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllUser(){
        Optional<List<User>> list = Optional.of(userRepository.findAll());
        return ResponseEntity.ok(list);

    }

    @PutMapping("/update_status")
    public ResponseEntity<String> updateOrderStatus(@RequestParam List<Long> ids, @RequestParam Boolean newStatus) {
        List<User> orders = userRepository.findAllById(ids);

        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy đơn hàng với các ID được cung cấp.");
        }

        orders.forEach(order -> {
            order.setStatus(newStatus);  // Cập nhật trạng thái mới
           userRepository.save(order);      // Lưu lại thay đổi
        });

        return ResponseEntity.ok("Trạng thái của các đơn hàng đã được cập nhật thành công.");
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

//    Hàm lấy danh sách review dựa vào userId
@Autowired
private final ReviewRepository reviewRepository;
    @GetMapping("/getReview/{userId}")
    public ResponseEntity<?> getReviewsByUserId(@PathVariable Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);

        if (reviews.isEmpty()) {
            return ResponseEntity.status(404).body("No reviews found for user ID: " + userId);
        }

        return ResponseEntity.ok(reviews);
    }

    @PutMapping("update_pass")
    public ResponseEntity<?> updatePass(@RequestBody UpdatePassDTO updatePassDTO){
        Optional<User> userOptional = userRepository.findByUsername((updatePassDTO.getUsername()));
        User user =  userOptional.get();
        if (Objects.equals(user.getRole(), "admin")) {
            if (Objects.equals(user.getPassword(), updatePassDTO.getOldPass())) {
                user.setPassword(updatePassDTO.getNewPass());
                userRepository.save(user);
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.badRequest().body(user);
    }




}
