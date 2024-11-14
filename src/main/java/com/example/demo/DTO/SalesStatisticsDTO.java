package com.example.demo.DTO;

public class SalesStatisticsDTO {
    private Long id;
    private String name;
    private Long totalQuantity; // Đổi thành Long
    private double totalRevenue;
    private double contribution;

    // Constructor, Getters, and Setters

    public SalesStatisticsDTO(Long id, String name, Long totalQuantity, double totalRevenue, double contribution) {
        this.id = id;
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.totalRevenue = totalRevenue;
        this.contribution = contribution;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getContribution() {
        return contribution;
    }

    public void setContribution(double contribution) {
        this.contribution = contribution;
    }
}
