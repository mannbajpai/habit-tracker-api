package com.example.habit.repository;

import com.example.habit.model.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabitRepository extends MongoRepository<Habit, String> {
    Page<Habit> findByUserId(String userId, Pageable pageable);
}
