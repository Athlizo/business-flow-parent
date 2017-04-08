package com.lizo.busflow.exception;

/**
 * Created by lizhou on 2017/4/7/007.
 */
public class NoRoutingException extends Exception {

    public NoRoutingException() {
    }

    public NoRoutingException(String message) {
        super(message);
    }

    public NoRoutingException(String message, Throwable cause) {
        super(message, cause);
    }
}
