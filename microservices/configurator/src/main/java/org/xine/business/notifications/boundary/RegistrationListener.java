package org.xine.business.notifications.boundary;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;

import org.xine.business.registations.entity.Workshop;

@Singleton
public class RegistrationListener {

	private final CopyOnWriteArrayList<Workshop> cache = new CopyOnWriteArrayList<>();

	@PostConstruct
	public void onStart() {
		System.out.println("---------Started!");
	}

	@Asynchronous
	public Future<Void> onNewRegistration(@Observes final Workshop workshop) {
		System.out.println("----------------onNewRegistration");
		this.cache.add(workshop);
		return new AsyncResult<Void>(null);
	}

	@Schedule(minute = "*/2", second = "*/10", hour = "*", persistent = true)
	public void sendEmail() {
		System.out.println("---------- sendEmail" + new Date());

		for (final Workshop workshop : this.cache) {
			System.out.println("Send workshop via email: " + workshop);
			this.cache.remove(workshop);
		}
	}

}
