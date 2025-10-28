package com.example.habit.service;

import com.example.habit.model.Habit;
import com.example.habit.model.HabitEntry;
import com.example.habit.repository.HabitEntryRepository;
import com.example.habit.repository.HabitRepository;
import com.example.habit.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitEntryRepository entryRepository;

    public HabitService(HabitRepository habitRepository, HabitEntryRepository entryRepository) {
        this.habitRepository = habitRepository;
        this.entryRepository = entryRepository;
    }

    public Page<Habit> getHabitsForUser(String userId, int page, int size) {
        return habitRepository.findByUserId(userId, PageRequest.of(page, size));
    }

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public void deleteHabit(String habitId, String userId) {
        Habit h = habitRepository.findById(habitId).orElseThrow(() -> new ResourceNotFoundException("Habit not found"));
        if (!h.getUserId().equals(userId)) throw new RuntimeException("unauthorized");
        // delete entries
        List<HabitEntry> entries = entryRepository.findByHabitId(habitId);
        entryRepository.deleteAll(entries);
        habitRepository.deleteById(habitId);
    }

    public HabitEntry addOrUpdateEntry(String habitId, LocalDate date, boolean completed) {
        HabitEntry entry = entryRepository.findByHabitIdAndDate(habitId, date)
                .orElse(HabitEntry.builder().habitId(habitId).date(date).completed(false).build());
        entry.setCompleted(completed);
        return entryRepository.save(entry);
    }

    public void deleteEntry(String entryId) {
        entryRepository.deleteById(entryId);
    }
}
