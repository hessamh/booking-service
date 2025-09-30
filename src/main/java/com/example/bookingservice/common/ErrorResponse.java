package com.example.bookingservice.common;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

public class ErrorResponse {

    private final OffsetDateTime timestamp;
    private final String path;
    private final String message;
    private final String code;
    private final List<FieldErrorItem> errors;

    public ErrorResponse(OffsetDateTime timestamp, String path, String message, String code, List<FieldErrorItem> errors) {
        this.timestamp = timestamp;
        this.path = path;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public String getMessage() { return message; }
    public String getCode() { return code; }
    public List<FieldErrorItem> getErrors() { return errors; }

    public static class FieldErrorItem {
        private final String field;
        private final String reason;

        public FieldErrorItem(String field, String reason) {
            this.field = field;
            this.reason = reason;
        }

        public String getField() { return field; }
        public String getReason() { return reason; }
    }

    public static ErrorResponse of(String path, String message, String code, List<FieldErrorItem> errors) {
        return new ErrorResponse(OffsetDateTime.now(), path, message, code, errors);
    }
}
