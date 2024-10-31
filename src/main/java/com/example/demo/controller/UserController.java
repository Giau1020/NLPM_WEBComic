package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequest loginRequest, HttpSession session) {
    Map<String, String> response = new HashMap<>();

    Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

    if (userOpt.isPresent()) {
        User user = userOpt.get();

        // Kiểm tra nếu tài khoản bị khóa (status = 0)
        if (Boolean.FALSE.equals(user.getStatus())) {
            response.put("message", "Tài khoản bị khóa");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Kiểm tra mật khẩu
        if (user.getPassword().equals(loginRequest.getPassword())) {
            session.setAttribute("userId", user.getId());
            System.out.println("Set userId in session: " + session.getAttribute("userId"));

            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Sai tên đăng nhập hoặc mật khẩu!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    } else {
        response.put("message", "Người dùng không tồn tại");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
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


    @PostMapping("/register")
public ResponseEntity<String> register(@RequestBody UserRegistrationRequest registrationRequest) {
    // Kiểm tra xem tên đăng nhập đã tồn tại chưa
    if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên đăng nhập đã tồn tại");
    }

    // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp nhau không
    if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mật khẩu xác nhận không khớp");
    }

    // Tạo người dùng mới
    User newUser = new User();
    newUser.setUsername(registrationRequest.getUsername());
    newUser.setPassword(registrationRequest.getPassword()); // Lưu mật khẩu không mã hóa
    newUser.setRole("user");
    newUser.setStatus(true);
    userRepository.save(newUser);

    // Tạo giỏ hàng rỗng cho người dùng mới
    Cart cart = new Cart();
    cart.setUser(newUser);
    cartRepository.save(cart);

    return ResponseEntity.ok("Đăng ký thành công");
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
        private String confirmPassword;
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

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
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
