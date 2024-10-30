package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.ComplaintService;
import com.example.demo.model.Complaint;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/submit")
    public String submitComplaint(
            @RequestParam("user_id") String userId,
            @RequestParam("full_name") String fullName,
            @RequestParam("id_order") Long orderId,
            @RequestParam("complaintTime")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime complaintTime,
            @RequestParam("description_complaint") String complaintContent,
            // @RequestParam(value = "attachment", required = false) MultipartFile attachment)
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

        // Tạo đối tượng Complaint
        Complaint complaint = new Complaint();
        complaint.setUserId(userId);
        complaint.setFullName(fullName);
        complaint.setOrderId(orderId);
        complaint.setComplaintTime(complaintTime);
        complaint.setDescriptionComplaint(complaintContent);
        // complaint.setStatusComplaint("Chưa giải quyết");

        // Xử lý tệp hình ảnh (nếu có)
        if (attachment != null && !attachment.isEmpty()) {
            String fileName = complaintService.saveFile(attachment); // Gọi service để lưu tệp
            complaint.setImage(fileName);
        }

        // Lưu khiếu nại vào cơ sở dữ liệu
        complaintService.submitComplaint(complaint);

        return "Gửi khiếu nại thành công!";
    }

    // lấy danh sách khiếu nại
    @GetMapping("/list")
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable long id) {
        Complaint complaint = complaintService.getComplaintById(id);
        if (complaint != null) {
            return ResponseEntity.ok(complaint);
        } else {
            return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy khiếu nại
        }
    }

    @PutMapping("/{id}/status-and-response")
    public ResponseEntity<String> updateComplaintStatusAndResponse(@PathVariable Long id,
            @RequestParam String statusComplaint,
            @RequestParam String response) {
        complaintService.updateComplaintStatusAndResponse(id, statusComplaint, response);
        return ResponseEntity.ok("Complaint status and response updated successfully");
    }

    // @GetMapping("/{user_id}")
    // public ResponseEntity<List<Complaint>> getComplaintsByUserId(@PathVariable("user_id") String userId) {
    //     // Gọi service để lấy danh sách khiếu nại theo user_id
    //     List<Complaint> complaints = complaintService.findComplaintsByUserId(userId);
    //     if (complaints.isEmpty()) {
    //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    //     }
    //     return new ResponseEntity<>(complaints, HttpStatus.OK);
    // }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Complaint>> getComplaintsByUserId(@PathVariable String userId) {
        List<Complaint> complaints = complaintService.getComplaintsByUserId(userId);
        return ResponseEntity.ok(complaints);
    }

}
