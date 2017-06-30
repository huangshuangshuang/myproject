package com.hss.exception;


public class CoreException extends RuntimeException {
    static final long serialVersionUID = -2134893902845766939L;

    public CoreException() {
        super();
    }

    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }
}
