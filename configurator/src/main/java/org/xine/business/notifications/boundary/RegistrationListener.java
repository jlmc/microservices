package org.xine.business.notifications.boundary;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.xine.business.registations.entity.Workshop;

@Singleton
public class RegistrationListener {

    private final CopyOnWriteArrayList<Workshop> cache = new CopyOnWriteArrayList<>();

    @Inject
    Logger logger;

    @PostConstruct
    public void onStart() {
        this.logger.info("---------Started!");
    }

    @Asynchronous
    public Future<Void> onNewRegistration(@Observes final Workshop workshop) {
        this.logger.info("----------------onNewRegistration");
        this.cache.add(workshop);
        return new AsyncResult<Void>(null);
    }

    @Schedule(minute = "*/1", second = "30", persistent = true)
    public void sendEmail() {
        this.logger.info("---------- sendEmail" + new Date());

        for (final Workshop workshop : this.cache) {
            this.logger.info("Send workshop via email: " + workshop);
            this.cache.remove(workshop);
        }
    }

}
