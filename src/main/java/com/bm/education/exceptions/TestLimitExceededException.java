package com.bm.education.exceptions;

public class TestLimitExceededException extends RuntimeException {
    public TestLimitExceededException(String message) {
        super(message);
    }
}
