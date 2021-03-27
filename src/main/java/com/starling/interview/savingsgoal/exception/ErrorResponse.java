package com.starling.interview.savingsgoal.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private final LocalDateTime timestamp;
    private final String errorMessage;

    public ErrorResponse(LocalDateTime timestamp, String errorMessage) {
        this.timestamp = timestamp;
        this.errorMessage = errorMessage;
    }
}
