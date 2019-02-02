package com.examples.monitorServerState.exception;

public enum ErrorCodes {
    NOT_CONNECTED("503"),
    PARSE_ERROR("1");

    ErrorCodes(String code) {
        this.code = code;
    }

    String code;

    public String getCode() {
        return this.code;
    }
}
