/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

/**
 *
 * @author SangNguyen
 */
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Role {

    @Id
    private String username;
    private String role;

    // Getters và Setters
}
