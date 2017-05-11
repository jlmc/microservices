package org.xine.extendable.business.color.control;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.xine.extendable.business.microscopes.method.MethodScoped;

@MethodScoped
public class Count implements Serializable {

	private final AtomicInteger count = new AtomicInteger();

	public int get() {
		return count.get();
	}

	public int add() {
		return count.incrementAndGet();
	}

	public boolean compareAndSet(int expect, int update) {
		return count.compareAndSet(expect, update);
	}

	public int remove() {
		return count.decrementAndGet();
	}

}
