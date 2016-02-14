package org.xine.channels.beanlocator;

public class BeanLocationException extends Exception {
	public BeanLocationException() {
	}

	public BeanLocationException(final String msg) {
		super(msg);
	}

	public BeanLocationException(final String msg, final Throwable cause) {
		super(msg, cause);
	}
}