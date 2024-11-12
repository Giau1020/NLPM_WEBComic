package com.example.demo.controller;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Comic;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.model.User;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ComicRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
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
    @Autowired
    private ComicRepository comicRepository;

    @PostMapping("/create")
public ResponseEntity<?> createOrder(@RequestBody Order order, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");
    
    // Kiểm tra xem userId có tồn tại trong session không
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

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

    // Kiểm tra tồn kho trước khi xử lý đơn hàng
    for (CartItem cartItem : cartItems) {
        Comic comic = cartItem.getComic();
        
        // Kiểm tra số lượng tồn kho
        if (comic.getQuantity() < cartItem.getQuantity()) {
            // Trả về phản hồi với mã trạng thái xung đột và thông báo
            cartItem.setQuantity(comic.getQuantity());
            cartItemRepository.save(cartItem); // Lưu lại cập nhật trong giỏ hàng
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Số lượng sản phẩm " + comic.getName() + " trong kho đã thay đổi. Vui lòng thử lại.");
        }
    }

    // Tiếp tục xử lý đơn hàng nếu số lượng tồn kho đủ
    order.setOrderTime(LocalDateTime.now().withNano(0));
    order.setOrderStatus("Chờ xác nhận");
    order.setUserId(userId);
    System.out.println(order.getPayment());
    order.setPayment(order.getPayment());
    Order savedOrder = orderRepository.save(order);
    
    for (CartItem cartItem : cartItems) {
        Comic comic = cartItem.getComic();
        comic.setQuantity(comic.getQuantity() - cartItem.getQuantity());
        comicRepository.save(comic);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(savedOrder);
        orderItem.setComic(cartItem.getComic());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getComic().getPrice());

        // Lưu OrderItem vào cơ sở dữ liệu
        orderItemRepository.save(orderItem);
    }

    return ResponseEntity.ok(savedOrder);
}

///////////////////////////////////
@PutMapping("/cancel/{orderId}")
public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, HttpSession session) {
    Long userId = (Long) session.getAttribute("userId");

    // Kiểm tra xem người dùng có đăng nhập hay không
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

    // Tìm đơn hàng theo orderId và userId
    Optional<Order> orderOpt = orderRepository.findByIdAndUserId(orderId, userId);
    if (!orderOpt.isPresent()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
    }

    Order order = orderOpt.get();

    // Kiểm tra xem trạng thái đơn hàng có phải là "Chờ xác nhận" hay không
    if (!order.getOrderStatus().equals("Chờ xác nhận")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be cancelled");
    }

    // Cập nhật trạng thái đơn hàng thành "Đã hủy"
    order.setOrderStatus("Đã hủy");
    orderRepository.save(order);

    // Phục hồi số lượng sản phẩm trong kho
    List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
    for (OrderItem orderItem : orderItems) {
        Comic comic = orderItem.getComic();
        comic.setQuantity(comic.getQuantity() + orderItem.getQuantity());
        comicRepository.save(comic); // Cập nhật lại số lượng sản phẩm
    }

    return ResponseEntity.ok("Order cancelled and stock updated");
}
/////////////////////////////////////
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
