package org.xine.presentation;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

import org.xine.business.registations.boundary.WorkshopRegistration;
import org.xine.business.registations.entity.Workshop;

@Presenter
public class Index {

    @EJB
    WorkshopRegistration ws;

    @Inject
    Logger logger;

    private final Workshop workshop = new Workshop();

    @PostConstruct
    public void onInitialize() {
        // not needed -> workshop
        this.logger.info("Initialized !");
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
