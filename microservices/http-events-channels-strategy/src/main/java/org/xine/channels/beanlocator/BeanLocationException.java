package org.xine.channels.beanlocator;

public class BeanLocationException extends Exception {
    private static final long serialVersionUID = 1L;

    public BeanLocationException() {
    }

    public BeanLocationException(final String msg) {
        super(msg);
    }

    public BeanLocationException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}