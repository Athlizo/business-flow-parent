package com.lizo.busflow.exception;

/**
 * Created by lizhou on 2017/4/8/008.
 */
public class MaxPathException extends RuntimeException {
    public MaxPathException() {
    }

    public MaxPathException(String message) {
        super(message);
    }

    public MaxPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
