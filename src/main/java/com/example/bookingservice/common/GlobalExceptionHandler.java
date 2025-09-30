package com.example.bookingservice.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        var body = ErrorResponse.of(req.getRequestURI(), ex.getMessage(), "NOT_FOUND", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException ex, HttpServletRequest req) {
        var body = ErrorResponse.of(req.getRequestURI(), ex.getMessage(), "CONFLICT", null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

//    @ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class })
//    public ResponseEntity<?> handleValidation(Exception ex, HttpServletRequest req) {
//        var binding = ex instanceof MethodArgumentNotValidException manve
//                ? manve.getBindingResult()
//                : ((BindException) ex).getBindingResult();
//
//        var errors = binding.getFieldErrors().stream()
//                .map(fe -> ErrorResponse.FieldErrorItem.builder()
//                        .field(fe.getField())
//                        .reason(fe.getDefaultMessage())
//                        .build())
//                .collect(Collectors.toList());
//
//        var body = ErrorResponse.of(req.getRequestURI(), "Validation failed", "BAD_REQUEST", errors);
//        return ResponseEntity.badRequest().body(body);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
//var errors = ex.getConstraintViolations().stream()
//        .map(cv -> new ErrorResponse.FieldErrorItem(cv.getPropertyPath().toString(), cv.getMessage()))
//        .collect(Collectors.toList());
//
//        var body = ErrorResponse.of(req.getRequestURI(), "Validation failed", "BAD_REQUEST", errors);
//        return ResponseEntity.badRequest().body(body);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleOther(Exception ex, HttpServletRequest req) {
//        var body = ErrorResponse.of(req.getRequestURI(), "Internal error", "INTERNAL_ERROR", null);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
//    }
}
