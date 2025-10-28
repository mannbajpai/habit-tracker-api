package com.example.habit.repository;

import com.example.habit.model.HabitEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitEntryRepository extends MongoRepository<HabitEntry, String> {
    List<HabitEntry> findByHabitId(String habitId);
    List<HabitEntry> findByHabitIdAndDateBetween(String habitId, LocalDate start, LocalDate end);
    Optional<HabitEntry> findByHabitIdAndDate(String habitId, LocalDate date);
    List<HabitEntry> findByDateBetweenAndHabitIdIn(LocalDate start, LocalDate end, List<String> habitIds);
    List<HabitEntry> findByDateBetweenAndHabitId(LocalDate start, LocalDate end, String habitId);
}
