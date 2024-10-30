package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findByUsername(String username);

    String findPhonenumberByUsername(String username); // lấy số điện thoại
}
