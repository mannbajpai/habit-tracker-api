package com.example.habit.controller;

import com.example.habit.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/habits/stats")
public class StatsController {
    private final StatsService statsService;
    public StatsController(StatsService statsService) { this.statsService = statsService; }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(Authentication auth) {
        String userId = (String) auth.getPrincipal();
        return ResponseEntity.ok(statsService.dashboard(userId));
    }
}
