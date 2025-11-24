package com.victorbarbosa.fintracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class TransactionNotFoundException extends FintrackerException {

    public TransactionNotFoundException(String detail) {
        super(detail);
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Transaction not found");
        pd.setDetail(getMessage());
        return pd;
    }
}
