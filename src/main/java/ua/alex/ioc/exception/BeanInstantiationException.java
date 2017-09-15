package ua.alex.ioc.exception;

public class BeanInstantiationException extends RuntimeException{
    public BeanInstantiationException(String message) {
        super(message);
    }

    public BeanInstantiationException(Throwable cause) {
        super(cause);
    }
}
