package com.gcash.exam.exception;

public class ParcelException extends RuntimeException {
    private final ErrorCode errorCode;

    public ParcelException(int code) {
        super(ErrorCode.fromCode(code).getMessage());
        this.errorCode = ErrorCode.fromCode(code);
    }

    public ParcelException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
