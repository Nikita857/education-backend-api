package com.bm.education.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum CourseFormat {
    VIDEO("Видео"),
    TEXT("Текст"),
    SCORM("SCORM"),
    WEBINAR("Вебинар"),
    INTERACTIVE("Интерактивный"),
    SIMULATION("Симуляция"),
    TEST("Тест");

    private final String displayName;

    CourseFormat(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}