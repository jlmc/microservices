package org.xine.business.control;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.xine.business.scopes.ManualScoped;

@ManualScoped
public class Control {
	public final static AtomicInteger INSTANCE_COUNTER = new AtomicInteger(0);

	@Inject
	AnotherControl anotherService;

	@Inject
	Instance<DependentControl> dependentService;

	@PostConstruct
	public void onCreate() {
		INSTANCE_COUNTER.incrementAndGet();
	}

	public String execute() {
		final DependentControl dependentControl = dependentService.get();

		return anotherService.execute() + dependentControl.execute();
	}

	@PreDestroy
	public void onDestroy() {
		System.out.println(this.getClass().getName() + " onDestroy");
		INSTANCE_COUNTER.decrementAndGet();
	}

}
