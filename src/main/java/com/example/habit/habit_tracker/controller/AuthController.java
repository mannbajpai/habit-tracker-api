package com.example.habit.controller;

import com.example.habit.dto.AuthRequest;
import com.example.habit.dto.AuthResponse;
import com.example.habit.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest req) {
        AuthResponse r = authService.register(req);
        return ResponseEntity.ok(r);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        AuthResponse r = authService.login(req);
        return ResponseEntity.ok(r);
    }
}
