package org.xine.batch.business.httpevents.eventsource;

import java.sql.Date;
import java.time.Instant;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@Singleton
public class EventGenerator {
    @Inject
    Event<String> message;

    @Schedule(minute = "*", second = "*/1", hour = "*")
    public void sendTime() {
        this.message.fire(Date.from(Instant.now()).toString());
        System.out.println(".");
    }
}
