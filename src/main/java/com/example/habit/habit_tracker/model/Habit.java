package com.example.habit.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "habits")
public class Habit {
    @Id
    private String id;
    private String name;
    private String description;
    private String color;
    private String icon;
    private String frequency; // e.g., "daily"
    private String userId;
}
