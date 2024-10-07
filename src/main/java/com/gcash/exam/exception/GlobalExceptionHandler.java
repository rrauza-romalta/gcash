package com.gcash.exam.exception;

import com.gcash.exam.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(getGenericBadRequest(ex.getMessage()));
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> handleGenericException(Exception ex) {
        return ResponseEntity.badRequest().body(getGenericBadRequest(ex.getMessage()));
    }

    private ResponseDTO<String> getGenericBadRequest(String message) {
        return new ResponseDTO<>(
                ErrorCode.BAD_REQUEST.getCode(),
                message,
                null,
                false);
    }
}