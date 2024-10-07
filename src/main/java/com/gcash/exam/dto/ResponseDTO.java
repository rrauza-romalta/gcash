package com.gcash.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
    private int errorCode = 0;
    private String message;
    private T responseData;
    private boolean success;
}
