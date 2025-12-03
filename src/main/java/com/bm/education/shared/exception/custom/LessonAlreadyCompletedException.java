package com.bm.education.shared.exception.custom;

public class LessonAlreadyCompletedException extends RuntimeException {
    public LessonAlreadyCompletedException(String message) {
        super(message);
    }
}
