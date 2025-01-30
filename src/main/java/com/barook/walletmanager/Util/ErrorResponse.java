package com.barook.walletmanager.Util;

import java.time.LocalDateTime;

// Error Response Class (DTO)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String details;

    // Constructor, getters...

    public ErrorResponse(LocalDateTime timestamp, int status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}