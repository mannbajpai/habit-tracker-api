package com.example.habit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "habit_entries")
public class HabitEntry {
    @Id
    private String id;
    private String habitId;
    private LocalDate date;
    private boolean completed;
}
