package com.example.habit.dto;
import lombok.*;
import java.time.LocalDate;
@Data
public class EntryDto {
    private LocalDate date; // e.g., 2025-10-28
    private boolean completed;
}
