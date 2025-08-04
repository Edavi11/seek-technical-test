package com.seek.test.seek_test.dto;

public class AuthResponseDto {
    
    private String token;
    
    // Default constructor
    public AuthResponseDto() {}
    
    // Constructor with token
    public AuthResponseDto(String token) {
        this.token = token;
    }
    
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
} 