package com.bm.education.api.exception.custom;

public class ReviewDuplicateException extends RuntimeException {
    public ReviewDuplicateException(String message) {
        super(message);
    }
}
