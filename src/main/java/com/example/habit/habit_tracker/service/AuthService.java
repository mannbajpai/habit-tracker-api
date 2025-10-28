package com.example.habit.service;

import com.example.habit.dto.AuthRequest;
import com.example.habit.dto.AuthResponse;
import com.example.habit.model.User;
import com.example.habit.repository.UserRepository;
import com.example.habit.config.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse register(AuthRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new RuntimeException("username taken");
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("email taken");
        }
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .build();
        User saved = userRepository.save(user);
        String token = jwtUtils.generateToken(saved.getUsername(), saved.getId());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest req) {
        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("invalid credentials");
        }
        String token = jwtUtils.generateToken(user.getUsername(), user.getId());
        return new AuthResponse(token);
    }
}
