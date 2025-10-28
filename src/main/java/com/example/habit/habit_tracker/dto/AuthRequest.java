package com.example.habit.dto;
import lombok.*;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String email; // optional for login
}
