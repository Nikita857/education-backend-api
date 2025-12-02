package com.bm.education.api.exception.custom;

public class LessonAlreadyCompletedException extends RuntimeException {
    public LessonAlreadyCompletedException(String message) {
        super(message);
    }
}
