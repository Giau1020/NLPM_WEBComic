package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Complaint;

public interface ComplaintAdminRepository extends JpaRepository<Complaint, Long> {

}
