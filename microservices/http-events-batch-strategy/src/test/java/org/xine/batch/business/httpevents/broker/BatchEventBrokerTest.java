package org.xine.batch.business.httpevents.broker;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xine.batch.business.httpevents.publish.BrowserWindow;

public class BatchEventBrokerTest {

    BatchEventBroker broker;

    @Before
    public void initialize() {
        this.broker = new BatchEventBroker();
    }

    @Test
    public void notifyWindowsWithoutEvents() {
        final BrowserWindow browserWindow = Mockito.mock(BrowserWindow.class);
        this.broker.onBrowersRequest(browserWindow);
        this.broker.sendEventBatch();

        Mockito.verify(browserWindow).nothingToSay();
        Mockito.verify(browserWindow).commit();
        Mockito.verify(browserWindow, Mockito.never()).sendAndCommit(Mockito.anyString());
    }

    @Test
    public void notifyWindowsWithEvents() {
        final String myMessage = "my message";
        final BrowserWindow bw = Mockito.mock(BrowserWindow.class);
        this.broker.onBrowersRequest(bw);
        this.broker.onNewEvent(myMessage);
        this.broker.sendEventBatch();

        Mockito.verify(bw, Mockito.never()).nothingToSay();
        Mockito.verify(bw, Mockito.never()).sendAndCommit(Mockito.anyString());
        Mockito.verify(bw).send(myMessage);
        Mockito.verify(bw).commit();
    }

    @Test
    public void sendBatchWithoutWindows() {
        this.broker.onNewEvent("message one");
        this.broker.onNewEvent("message two");
        this.broker.sendEventBatch();

        // nothing to verify
        // Mockito.verify(this.)
    }


}
