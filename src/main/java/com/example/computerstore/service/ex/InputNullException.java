package com.example.computerstore.service.ex;


public class InputNullException extends ServiceException{
    public InputNullException() {
        super();
    }

    public InputNullException(String message) {
        super(message);
    }

    public InputNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputNullException(Throwable cause) {
        super(cause);
    }

    protected InputNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
