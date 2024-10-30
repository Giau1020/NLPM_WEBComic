package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Xử lý đăng nhập
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/login1")
    public String login() {
        return "login"; // Tên của file login.html trong thư mục templates (nếu bạn dùng Thymeleaf)
    }

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
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setPassword(registrationRequest.getPassword());
        newUser.setRole("user");

        // Mã hóa mật khẩu nếu cần
        userRepository.save(newUser);

        // Tạo giỏ hàng rỗng cho người dùng mới
        Cart cart = new Cart();
        cart.setUser(newUser);
        cartRepository.save(cart);

        return ResponseEntity.ok("User registered successfully");
        // return ResponseEntity.status(HttpStatus.SEE_OTHER)
        //                  .header("Location", "/login")
        //                  .build();
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

    // lấy username
    @GetMapping("/info/username")
    public ResponseEntity<String> getUsername(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            String username = userOpt.get().getUsername();  // Lấy username từ đối tượng User
            return ResponseEntity.ok(username);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    // Phương thức trả về email của người dùng

    @GetMapping("/info/email")
    public ResponseEntity<String> getEmail(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            String email = userOpt.get().getEmail();  // Lấy email từ đối tượng User
            return ResponseEntity.ok(email);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/info/phonenumber")
    public ResponseEntity<String> getPhonenumber(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            String phonenumber = userOpt.get().getPhonenumber();  // Lấy email từ đối tượng User
            return ResponseEntity.ok(phonenumber);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // lấy giới tính
    @GetMapping("/info/gender")
    public ResponseEntity<String> getGender(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            String gender = userOpt.get().getGender();  // Lấy gender từ đối tượng User
            return ResponseEntity.ok(gender);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // lấy ngày sinh
    @GetMapping("/info/birthdate")
    public ResponseEntity<String> getBirthdate(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            LocalDate birthdate = userOpt.get().getBirthdate();  // Lấy ngày sinh từ đối tượng User
            return ResponseEntity.ok(birthdate.toString()); // Trả về dưới dạng chuỗi
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
        private String email;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

    /**
     * @return UserRepository return the userRepository
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * @param userRepository the userRepository to set
     */
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return CartRepository return the cartRepository
     */
    public CartRepository getCartRepository() {
        return cartRepository;
    }

    /**
     * @param cartRepository the cartRepository to set
     */
    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

}
