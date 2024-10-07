package com.gcash.exam.exception;

import com.gcash.exam.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ParcelException.class)
    public ResponseEntity<ResponseDTO<String>> handleParcelException(ParcelException ex) {
        ResponseDTO<String> errorResponse = new ResponseDTO<>(
                                        ex.getErrorCode().getCode(),
                                        ex.getErrorCode().getMessage(),
                                null,
                                false);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}