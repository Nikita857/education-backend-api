package com.bm.education.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CourseDifficultyLevel {
    BEGINNER("Начинающий"),
    INTERMEDIATE("Средний"),
    ADVANCED("Продвинутый"),
    EXPERT("Эксперт");

    private final String displayName;

    CourseDifficultyLevel(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}