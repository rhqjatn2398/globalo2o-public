package com.example.globalO2O.login.domain.exception;

public class DuplicateRecommendationException extends Exception{
    public DuplicateRecommendationException() {
    }

    public DuplicateRecommendationException(String message) {
        super(message);
    }

    public DuplicateRecommendationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateRecommendationException(Throwable cause) {
        super(cause);
    }

    public DuplicateRecommendationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
