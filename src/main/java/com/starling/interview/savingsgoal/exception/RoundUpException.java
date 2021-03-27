package com.starling.interview.savingsgoal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoundUpException extends RuntimeException {
    public RoundUpException(String message) {
        super(message);
    }
}
