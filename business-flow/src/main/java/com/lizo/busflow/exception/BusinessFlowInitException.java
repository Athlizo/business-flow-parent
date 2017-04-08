package com.lizo.busflow.exception;

/**
 * 初始化的时候排除异常
 * Created by lizhou on 2017/3/14/014.
 */
public class BusinessFlowInitException extends RuntimeException {
    public BusinessFlowInitException() {
    }

    public BusinessFlowInitException(String message) {
        super(message);
    }

    public BusinessFlowInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessFlowInitException(Throwable cause) {
        super(cause);
    }

}
