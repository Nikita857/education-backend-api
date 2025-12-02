package com.bm.education.api.exception.custom;

public class TestLimitExceededException extends RuntimeException {
    public TestLimitExceededException(String message) {
        super(message);
    }
}
