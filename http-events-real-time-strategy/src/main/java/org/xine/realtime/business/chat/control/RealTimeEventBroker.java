package org.xine.realtime.business.chat.control;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class RealTimeEventBroker {

    private final ConcurrentLinkedQueue<BrowserWindow> browers = new ConcurrentLinkedQueue<>();

    public void onBrowserRequest(@Observes final BrowserWindow browserWindow) {
        this.browers.add(browserWindow);
    }

    // the Asynchronous is optional,
    // verify the necessity o that with stress Tests
    @Asynchronous
    public void onNewEvent(@Observes final String message) {
        this.browers.forEach(brower -> {
            try {
                brower.sendAndCommit(message);
            } finally {
                this.browers.remove(brower);
            }
        });
    }

}
