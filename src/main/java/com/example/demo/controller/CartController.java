package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Comic;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ComicRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.CartItemRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

      @Autowired
    private UserRepository userRepository;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest request, HttpSession session) {
        // Lấy userId từ session sau khi đăng nhập
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        System.out.println("UserId from session: " + userId);
        // Tìm giỏ hàng của người dùng
        User user = new User();
        user.setId(userId);
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        if (!cartOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user ID: " + userId);
        }

        Cart cart = cartOptional.get();

        // Tìm sản phẩm (truyện tranh)
        Optional<Comic> comicOptional = comicRepository.findById(request.getComicId());
        if (!comicOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comic not found with ID: " + request.getComicId());
        }

        Comic comic = comicOptional.get();

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndComic(cart, comic);
        if (existingCartItem.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ hàng, tăng số lượng
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            // Nếu chưa có trong giỏ hàng, tạo một mục mới
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setComic(comic);
            cartItem.setQuantity(request.getQuantity());
            cartItemRepository.save(cartItem);
        }

        return ResponseEntity.ok("Comic added to cart!");
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<CartItem>> getUserCart(HttpSession session) {
        // Lấy userId từ session sau khi người dùng đã đăng nhập
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).build(); // 401 Unauthorized nếu chưa đăng nhập
        }

        // Tìm người dùng trong cơ sở dữ liệu
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(404).build(); // 404 Not Found nếu người dùng không tồn tại
        }

        // Tìm giỏ hàng của người dùng
        Optional<Cart> cartOpt = cartRepository.findByUser(userOpt.get());
        if (!cartOpt.isPresent()) {
            return ResponseEntity.status(404).build(); // 404 Not Found nếu giỏ hàng không tồn tại
        }

        // Lấy danh sách các mục trong giỏ hàng
        List<CartItem> cartItems = cartItemRepository.findByCart(cartOpt.get());

        // Trả về danh sách mục giỏ hàng
        return ResponseEntity.ok(cartItems);
    }
    @PostMapping("/update")
public ResponseEntity<String> updateCartItem(@RequestBody CartItemRequest request, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

    Optional<User> userOpt = userRepository.findById(userId);
    if (!userOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    Optional<Cart> cartOpt = cartRepository.findByUser(userOpt.get());
    if (!cartOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user ID: " + userId);
    }

    Cart cart = cartOpt.get();
    Optional<Comic> comicOptional = comicRepository.findById(request.getComicId());
    if (!comicOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comic not found with ID: " + request.getComicId());
    }

    Comic comic = comicOptional.get();
    Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndComic(cart, comic);
    if (!existingCartItem.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found for comic ID: " + request.getComicId());
    }

    CartItem cartItem = existingCartItem.get();
    cartItem.setQuantity(request.getQuantity());
    cartItemRepository.save(cartItem);

    return ResponseEntity.ok("Cart item updated successfully!");
}
    @PostMapping("/remove")
public ResponseEntity<String> removeFromCart(@RequestBody CartItemRequest request, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

    Optional<User> userOpt = userRepository.findById(userId);
    if (!userOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    Optional<Cart> cartOpt = cartRepository.findByUser(userOpt.get());
    if (!cartOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user ID: " + userId);
    }

    Cart cart = cartOpt.get();
    Optional<Comic> comicOptional = comicRepository.findById(request.getComicId());
    if (!comicOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comic not found with ID: " + request.getComicId());
    }

    Comic comic = comicOptional.get();
    Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndComic(cart, comic);
    if (!existingCartItem.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found for comic ID: " + request.getComicId());
    }

    // Xóa sản phẩm khỏi giỏ hàng
    CartItem cartItem = existingCartItem.get();
    cartItemRepository.delete(cartItem);

    return ResponseEntity.ok("Cart item removed successfully!");
}


@PostMapping("/update-selected")
public ResponseEntity<String> updateSelected(@RequestBody CartItemRequest request, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

    Optional<User> userOpt = userRepository.findById(userId);
    if (!userOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    Optional<Cart> cartOpt = cartRepository.findByUser(userOpt.get());
    if (!cartOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user ID: " + userId);
    }

    Cart cart = cartOpt.get();
    Optional<Comic> comicOptional = comicRepository.findById(request.getComicId());
    if (!comicOptional.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comic not found with ID: " + request.getComicId());
    }

    Comic comic = comicOptional.get();
    Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndComic(cart, comic);
    if (!existingCartItem.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found for comic ID: " + request.getComicId());
    }

    CartItem cartItem = existingCartItem.get();
    cartItem.setSelected(request.isSelected());  // Cập nhật trạng thái checkbox
    cartItemRepository.save(cartItem);

    return ResponseEntity.ok("Cart item selection updated successfully!");
}


}

