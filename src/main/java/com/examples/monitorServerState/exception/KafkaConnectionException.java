package com.examples.monitorServerState.exception;

public class KafkaConnectionException extends Exception {
    public KafkaConnectionException(ErrorCodes code, String message) {
        super(code.getCode() + ":" + message);
    }

}
