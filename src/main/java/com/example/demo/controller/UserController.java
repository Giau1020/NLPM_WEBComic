package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Xử lý đăng nhập
     @Autowired
    private CartRepository cartRepository;

@PostMapping("/login")
public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest loginRequest, HttpSession session) {
    Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());
    
    if (userOpt.isPresent()) {
        User user = userOpt.get();
        if (user.getPassword().equals(loginRequest.getPassword())) {
            session.setAttribute("userId", user.getId());
            System.out.println("Set userId in session: " + session.getAttribute("userId"));

            // Trả về thông tin người dùng bao gồm role
            return ResponseEntity.ok(new UserLoginResponse(user.getRole()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

   
@GetMapping("/is-logged-in")
public ResponseEntity<Boolean> isLoggedIn(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    System.out.println("Checking logged-in status. UserId: " + userId);
    System.out.println("Session ID: " + session.getId());  // Kiểm tra session ID
    return ResponseEntity.ok(userId != null);
}



@PostMapping("/logout")
public void logout(HttpSession session, HttpServletResponse response) throws IOException {
    session.invalidate();  // Xóa session khi đăng xuất
    response.sendRedirect("/TrangChu.html");  // Chuyển hướng về trang chủ
}

 // Chức năng đăng ký
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest registrationRequest) {
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        // Tạo người dùng mới
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(registrationRequest.getPassword()); // Mã hóa mật khẩu nếu cần
        userRepository.save(newUser);

        // Tạo giỏ hàng rỗng cho người dùng mới
        Cart cart = new Cart();
        cart.setUser(newUser);
        cartRepository.save(cart);

        return ResponseEntity.ok("User registered successfully");
    }
    
     @GetMapping("/info")
    public ResponseEntity<User> getUserInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());  // Trả về toàn bộ đối tượng User dưới dạng JSON
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    // Lớp để nhận dữ liệu đăng nhập từ client
    static class UserLoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    
    // Lớp để nhận dữ liệu đăng ký từ client
    static class UserRegistrationRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    static class UserLoginResponse {
    private String role;

    public UserLoginResponse(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
}
