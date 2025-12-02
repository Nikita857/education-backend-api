package com.bm.education.quiz.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionType {
    SINGLE_CHOICE("Один правильный ответ"),
    MULTIPLE_CHOICE("Несколько правильных ответов"),
    TEXT_RESPONSE("Текстовый ответ"),
    MATCHING("Сопоставление");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}