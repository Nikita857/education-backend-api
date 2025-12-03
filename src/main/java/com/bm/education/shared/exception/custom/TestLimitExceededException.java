package com.bm.education.shared.exception.custom;

public class TestLimitExceededException extends RuntimeException {
    public TestLimitExceededException(String message) {
        super(message);
    }
}
