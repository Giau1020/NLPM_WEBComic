package com.example.demo.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Complaint;
import com.example.demo.repository.ComplaintRepository;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    //lộc khiếu nại theo userid
    // public List<Complaint> findComplaintsByUserId(String userId) {
    //     return complaintRepository.findByUserId(userId);
    // }
    // public ComplaintService(ComplaintRepository complaintRepository) {
    //     this.complaintRepository = complaintRepository;
    // }
    public List<Complaint> getComplaintsByUserId(String userId) {
        return complaintRepository.findByUserId(userId);
    }

    public Complaint submitComplaint(Complaint complaint) {
        // Set the complaint time and default status before saving
        complaint.setComplaintTime(LocalDateTime.now());
        complaint.setStatusComplaint("Chưa xử lý");
        return complaintRepository.save(complaint);
    }

    // xử lý ảnh
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("Tệp không hợp lệ!");
            return null;
        }

        String folder = "C:/Users/ASUS/Documents/NH/uploads"; // Đường dẫn lưu tệp
        String fileName = file.getOriginalFilename();

        if (fileName == null || fileName.isEmpty()) {
            System.out.println("Tên tệp không hợp lệ!");
            return null;
        }

        Path path = Paths.get(folder, fileName);

        try {
            Files.createDirectories(path.getParent()); // Tạo thư mục nếu chưa tồn tại
            Files.write(path, file.getBytes()); // Ghi tệp vào hệ thống
            return fileName; // Trả về tên tệp để lưu vào cơ sở dữ liệu
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi tệp: " + e.getMessage());
            e.printStackTrace();
            return null; // Xử lý lỗi nếu cần
        }
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint getComplaintById(long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    public Complaint updateComplaintStatusAndResponse(Long id, String statusComplaint, String response) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        // Chỉ cập nhật các trường liên quan
        complaint.setStatusComplaint(statusComplaint);
        complaint.setResponse(response);

        return complaintRepository.save(complaint);  // Lưu lại chỉ với 2 trường đã thay đổi
    }

}
