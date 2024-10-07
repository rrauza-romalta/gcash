package com.gcash.exam.exception;

import com.gcash.exam.dto.ResponseDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIllegalArgumentException_shouldReturnBadRequest() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");
        ResponseEntity<ResponseDTO<String>> response = handler.handleIllegalArgumentException(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid argument", response.getBody().getMessage());
    }

    @Test
    void handleParcelException_shouldReturnBadRequestWithErrorCode() {
        ParcelException ex = new ParcelException(ErrorCode.REJECTED);
        ResponseEntity<ResponseDTO<String>> response = handler.handleParcelException(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(ErrorCode.REJECTED.getCode(), response.getBody().getErrorCode());
        assertEquals(ErrorCode.REJECTED.getMessage(), response.getBody().getMessage());
    }

    @Test
    void handleGenericException_shouldReturnBadRequest() {
        Exception ex = new Exception("Generic error");
        ResponseEntity<ResponseDTO<String>> response = handler.handleGenericException(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Generic error", response.getBody().getMessage());
    }
}
