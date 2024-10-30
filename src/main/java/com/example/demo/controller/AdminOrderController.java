package com.example.demo.controller;

import com.example.demo.DTO.UpdateCustomerInfoRequest;
import com.example.demo.model.Comic;
import com.example.demo.model.Order;
import com.example.demo.model.OrderItem;
import com.example.demo.repository.ComicRepository;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/sng/admin/orders")
public class AdminOrderController {

    private final OrderRepository orderRepository;
    @Autowired
    public AdminOrderController(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ComicRepository comicRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.comicRepository = comicRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders(){
        try{
            List<Order> orders = orderRepository.findAll();
            return ResponseEntity.ok(orders);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PutMapping("/update_status")
    public ResponseEntity<String> updateOrderStatus(@RequestParam List<Long> ids, @RequestParam String newStatus) {
        List<Order> orders = orderRepository.findAllById(ids);

        if (orders.isEmpty()) {
            return ResponseEntity.status(404).body("Không tìm thấy đơn hàng với các ID được cung cấp.");
        }

        orders.forEach(order -> {
            order.setOrderStatus(newStatus);  // Cập nhật trạng thái mới
            orderRepository.save(order);      // Lưu lại thay đổi
        });

        return ResponseEntity.ok("Trạng thái của các đơn hàng đã được cập nhật thành công.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);

        return order.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Update customer information
    @PutMapping("/{orderId}/updateCustomerInfo")
    public ResponseEntity<?> updateCustomerInfo(
            @PathVariable Long orderId,
            @RequestBody UpdateCustomerInfoRequest request) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        Order order = orderOptional.get();
        order.setFullName(request.getFullName());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setProvince(request.getProvince());
        order.setDistrict(request.getDistrict());
        order.setWard(request.getWard());
        order.setAddress(request.getAddress());

        orderRepository.save(order);

        return ResponseEntity.ok(order);
    }
private  final  OrderItemRepository orderItemRepository;
    private final ComicRepository comicRepository;

    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, HttpSession session) {
       // Long userId = (Long) session.getAttribute("userId");



        // Tìm đơn hàng theo orderId và userId
        Optional<Order> orderOpt = orderRepository.findById(orderId);
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

}
