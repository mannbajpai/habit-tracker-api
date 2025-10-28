package com.example.habit.controller;

import com.example.habit.dto.HabitDto;
import com.example.habit.dto.EntryDto;
import com.example.habit.model.Habit;
import com.example.habit.model.HabitEntry;
import com.example.habit.service.HabitService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService habitService;
    public HabitController(HabitService habitService) { this.habitService = habitService; }

    /**
     * GET /api/habits?page=0&size=10
     * Returns a Page<Habit> with pagination metadata.
     */
    @GetMapping
    public ResponseEntity<Page<Habit>> getHabits(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        String userId = (String) auth.getPrincipal();
        return ResponseEntity.ok(habitService.getHabitsForUser(userId, page, size));
    }

    @PostMapping
    public ResponseEntity<Habit> createHabit(@RequestBody HabitDto dto, Authentication auth) {
        String userId = (String) auth.getPrincipal();
        Habit h = Habit.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .color(dto.getColor())
                .icon(dto.getIcon())
                .frequency(dto.getFrequency())
                .userId(userId)
                .build();
        return ResponseEntity.ok(habitService.createHabit(h));
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<?> deleteHabit(@PathVariable String habitId, Authentication auth) {
        String userId = (String) auth.getPrincipal();
        habitService.deleteHabit(habitId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{habitId}/entries")
    public ResponseEntity<HabitEntry> addEntry(@PathVariable String habitId, @RequestBody EntryDto dto, Authentication auth) {
        LocalDate date = dto.getDate() != null ? dto.getDate() : LocalDate.now();
        HabitEntry saved = habitService.addOrUpdateEntry(habitId, date, dto.isCompleted());
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/entries/{entryId}")
    public ResponseEntity<?> deleteEntry(@PathVariable String entryId, Authentication auth) {
        habitService.deleteEntry(entryId);
        return ResponseEntity.noContent().build();
    }
}
