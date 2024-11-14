package com.example.demo.DTO;
public class DailyRevenueDTO {

    private int year;
    private int month;
    private int day;
    private double totalRevenue;

    public DailyRevenueDTO(int year, int month, int day, double totalRevenue) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.totalRevenue = totalRevenue;
    }

    // Getters và Setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}



