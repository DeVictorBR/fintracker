package com.victorbarbosa.fintracker.exception;

import org.springframework.http.ProblemDetail;

public abstract class FintrackerException extends RuntimeException {

    public FintrackerException(String detail) {
        super(detail);
    }

    public abstract ProblemDetail toProblemDetail();
}
