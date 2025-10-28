package com.example.habit.service;

import com.example.habit.model.Habit;
import com.example.habit.model.HabitEntry;
import com.example.habit.repository.HabitEntryRepository;
import com.example.habit.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {
    private final HabitRepository habitRepo;
    private final HabitEntryRepository entryRepo;

    public StatsService(HabitRepository habitRepo, HabitEntryRepository entryRepo) {
        this.habitRepo = habitRepo;
        this.entryRepo = entryRepo;
    }

    public Map<String, Object> dashboard(String userId) {
        List<Habit> habits = habitRepo.findByUserId(userId);
        List<String> habitIds = habits.stream().map(Habit::getId).collect(Collectors.toList());
        LocalDate today = LocalDate.now();

        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> habitStats = new ArrayList<>();

        for (Habit h : habits) {
            Map<String, Object> hs = new HashMap<>();
            hs.put("habitId", h.getId());
            hs.put("name", h.getName());

            // compute current streak (consecutive days up to today)
            int streak = computeCurrentStreak(h.getId(), today);
            hs.put("currentStreak", streak);

            // compute weekly completion %
            LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
            LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
            List<HabitEntry> weekEntries = entryRepo.findByHabitIdAndDateBetween(h.getId(), startOfWeek, endOfWeek);
            // days considered = number of days from startOfWeek to today if frequency daily
            long daysConsidered = java.time.temporal.ChronoUnit.DAYS.between(startOfWeek, today) + 1;
            long completedDays = weekEntries.stream().filter(HabitEntry::isCompleted).count();
            double weeklyPercent = daysConsidered == 0 ? 0.0 : (completedDays * 100.0 / daysConsidered);
            hs.put("weeklyCompletionPercent", Math.round(weeklyPercent * 100.0) / 100.0);

            habitStats.add(hs);
        }

        res.put("habits", habitStats);
        res.put("totalHabits", habits.size());
        return res;
    }

    private int computeCurrentStreak(String habitId, LocalDate today) {
        // count backwards from today while entry completed exists
        int streak = 0;
        LocalDate cursor = today;
        while (true) {
            Optional<HabitEntry> e = entryRepo.findByHabitIdAndDate(habitId, cursor);
            if (e.isPresent() && e.get().isCompleted()) {
                streak++;
                cursor = cursor.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }
}
