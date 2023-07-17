package com.example.computerstore.service.ex;

/**业务层异常的基类**/
public class ServiceException extends RuntimeException{
    //五个全部重写

    /**
     * 无参的异常
     */
    public ServiceException() {
        super();
    }

    /**
     * 待抛的信息
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 待抛的信息和对象
     * @param message
     * @param cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
