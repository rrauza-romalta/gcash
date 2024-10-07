package com.gcash.exam.exception;

public enum ErrorCode {
    REJECTED(1001, "Weight Exceeds 50kg"),
    VOUCHER_INVALID(2001, "Voucher code is invalid"),
    VOUCHER_UNVERIFIED(2002, "Unable to verify voucher"),
    VOUCHER_API_EXCEPTION(2003, "Bad request Voucher API"),
    BAD_REQUEST(3001, "Bad Request");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorCode fromCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + code);
    }
}