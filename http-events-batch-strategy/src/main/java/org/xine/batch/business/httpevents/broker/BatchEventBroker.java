package org.xine.batch.business.httpevents.broker;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;

import org.xine.batch.business.httpevents.publish.BrowserWindow;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class BatchEventBroker {

    private final ConcurrentLinkedQueue<BrowserWindow> browers = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<String> eventsCache = new ConcurrentLinkedQueue<>();

    public void onBrowersRequest(@Observes final BrowserWindow browserWindow) {
        this.browers.add(browserWindow);
    }

    public void onNewEvent(@Observes final String message) {
        this.eventsCache.add(message);
    }

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void sendEventBatch() {

        this.browers.forEach(brower -> {
            try {
                if(this.eventsCache.isEmpty()) {
                    brower.nothingToSay();
                } else {
                    this.eventsCache.forEach(brower::send);
                }

            } finally {
                brower.commit();
                this.browers.remove(brower);
            }
        });

        this.eventsCache.clear();
    }

}
