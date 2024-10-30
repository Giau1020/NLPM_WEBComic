package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByUserId(String userId);
}
