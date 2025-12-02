package com.bm.education.lesson.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LessonFormat {
    VIDEO("Видео"),
    TEXT("Текст"),
    SCORM("SCORM"),
    WEBINAR("Вебинар"),
    INTERACTIVE("Интерактивный"),
    SIMULATION("Симуляция"),
    TEST("Тест");

    private final String displayName;

    LessonFormat(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}