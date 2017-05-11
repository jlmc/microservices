package org.xine.business.control;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class DependentControl implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static AtomicInteger INSTANCE_COUNTER = new AtomicInteger(0);

	public DependentControl() {
		// nothing
	}

	@PostConstruct
	public void onCreate() {
		INSTANCE_COUNTER.incrementAndGet();
	}

	public String execute() {
		return "+";
	}

	@PreDestroy
	public void onDestroy() {
		System.out.println(this.getClass().getName() + " onDestroy");
		INSTANCE_COUNTER.decrementAndGet();
	}
}
