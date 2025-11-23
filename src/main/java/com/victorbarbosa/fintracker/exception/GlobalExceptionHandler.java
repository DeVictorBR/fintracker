package com.victorbarbosa.fintracker.exception;

import com.victorbarbosa.fintracker.exception.dto.InvalidParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FintrackerException.class)
    public ProblemDetail handleFintrackerException(FintrackerException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var invalidParams = e.getFieldErrors()
                .stream()
                .map(fe -> new InvalidParam(fe.getField(), fe.getDefaultMessage()))
                .toList();
        var pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Invalid request parameters");
        pd.setDetail("There is invalid fields on the request");
        pd.setProperty("invalid-params", invalidParams);
        return pd;
    }
}
