package com.bm.education.exceptions;

public class LessonAlreadyCompletedException extends RuntimeException {
    public LessonAlreadyCompletedException(String message) {
        super(message);
    }
}
