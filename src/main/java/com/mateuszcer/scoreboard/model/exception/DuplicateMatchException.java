package com.mateuszcer.scoreboard.model.exception;

public class DuplicateMatchException extends RuntimeException {
    public DuplicateMatchException(String message) {
        super(message);
    }
}
