package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;




@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

     @Autowired
    private UserRepository userRepository;
     
    @Autowired
    private CartRepository cartRepository;
     @Autowired
    private OrderItemRepository orderItemRepository;
     @Autowired
    private CartItemRepository cartItemRepository;
    @PostMapping("/create")
public ResponseEntity<?> createOrder(@RequestBody Order order, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    
    // Kiểm tra xem userId có tồn tại trong session không
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }
    
    
     order.setOrderTime(LocalDateTime.now().withNano(0));
    order.setOrderStatus("Chờ xác nhận"); // Trạng thái mặc định "Chờ xác nhận"
    order.setUserId(userId); // Gán userId cho đơn hàng
    
    // Lưu đơn hàng vào cơ sở dữ liệu và trả về đơn hàng đã lưu
    Order savedOrder = orderRepository.save(order);
    //
    
     Optional<User> userOpt = userRepository.findById(userId);
    if (!userOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    User user = userOpt.get();

    // Tìm giỏ hàng của người dùng
    Optional<Cart> cartOpt = cartRepository.findByUser(user);
    if (!cartOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user");
    }

    Cart cart = cartOpt.get();
List<CartItem> cartItems = cartItemRepository.findByCartAndSelectedTrue(cart);
    
    for (CartItem cartItem : cartItems) {
        // Tạo một OrderItem mới
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(savedOrder); // Gán đơn hàng đã lưu vào OrderItem
        orderItem.setComic(cartItem.getComic()); // Gán thông tin sản phẩm
        orderItem.setQuantity(cartItem.getQuantity()); // Gán số lượng sản phẩm
        orderItem.setPrice(cartItem.getComic().getPrice()); // Gán giá sản phẩm
        
        // Lưu OrderItem vào cơ sở dữ liệu
        orderItemRepository.save(orderItem);
    }
    return ResponseEntity.ok(savedOrder); // Trả về đối tượng Order đã lưu
}
///////////////////////////////////
@GetMapping("/user-orders")
public ResponseEntity<List<Order>> getUserOrders(HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    
    // Kiểm tra xem userId có tồn tại trong session không
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Lấy danh sách đơn hàng của người dùng từ cơ sở dữ liệu
    List<Order> userOrders = orderRepository.findByUserId(userId);

    return ResponseEntity.ok(userOrders);
}
////////////////////////////////////////////////////
@GetMapping("/details/{orderId}")
public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    
    // Kiểm tra xem userId có tồn tại trong session không
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Lấy đơn hàng theo orderId và userId
    Optional<Order> orderOpt = orderRepository.findByIdAndUserId(orderId, userId);
    if (!orderOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Trả về chi tiết đơn hàng
    return ResponseEntity.ok(orderOpt.get());
}

}
/*
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
     
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order, HttpSession session) {
        // Lấy userId từ session
        Long userId = (Long) session.getAttribute("userId");
        
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        
        // Lấy thông tin người dùng
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOpt.get();

        // Tìm giỏ hàng của người dùng
        Optional<Cart> cartOpt = cartRepository.findByUser(user);
        if (!cartOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found for user");
        }

        Cart cart = cartOpt.get();

        // Lấy các mục trong giỏ hàng với thuộc tính selected = true
        List<CartItem> cartItems = cartItemRepository.findByCartAndSelectedTrue(cart);
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No selected items in the cart");
        }

        // Tạo đơn hàng mới
        order.setOrderTime(LocalDateTime.now().withNano(0));
        order.setOrderStatus("Chờ xác nhận"); // Trạng thái mặc định "Chờ xác nhận"
        order.setUserId(userId); // Gán userId cho đơn hàng

        // Lưu đơn hàng vào cơ sở dữ liệu
        Order savedOrder = orderRepository.save(order);

        // Duyệt qua các CartItem và lưu thành OrderItem
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder); // Gán đơn hàng đã lưu vào OrderItem
            orderItem.setComic(cartItem.getComic()); // Gán thông tin sản phẩm
            orderItem.setQuantity(cartItem.getQuantity()); // Gán số lượng sản phẩm
            orderItem.setPrice(cartItem.getComic().getPrice()); // Gán giá sản phẩm
            
            // Lưu OrderItem vào cơ sở dữ liệu
            orderItemRepository.save(orderItem);
        }

        // Trả về phản hồi với đơn hàng đã lưu
        return ResponseEntity.ok(savedOrder);
    }
}
*/