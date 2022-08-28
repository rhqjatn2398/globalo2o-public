package com.example.globalO2O.login.domain.exception;

public class DuplicateMemberException extends RuntimeException {
    public DuplicateMemberException() {
    }

    public DuplicateMemberException(String message) {
        super(message);
    }

    public DuplicateMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateMemberException(Throwable cause) {
        super(cause);
    }
}
