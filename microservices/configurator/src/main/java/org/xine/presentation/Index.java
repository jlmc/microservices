package org.xine.presentation;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import org.xine.business.registations.boundary.WorkshopRegistration;
import org.xine.business.registations.entity.Workshop;

@Presenter
public class Index {

	@EJB
	WorkshopRegistration ws;

	private final Workshop workshop = new Workshop();

	@PostConstruct
	public void onInitialize() {
		// not needed -> workshop
		System.out.println("Initialized !");
	}

	public Workshop getWorkshop() {
		return this.workshop;
	}

	public Object newRegistration() {
		try {
			this.ws.register(this.workshop);
		} catch (final Exception e) {
			throw new IllegalStateException("Cannot invoke boundary");
		}
		return "registered";
	}

	public String getHello() {
		return "Novatec from ejb @" + this.ws.getDate();
	}
}
