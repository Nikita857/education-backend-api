package com.bm.education.shared.exception.custom;

public class ReviewDuplicateException extends RuntimeException {
    public ReviewDuplicateException(String message) {
        super(message);
    }
}
