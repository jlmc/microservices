package org.xine.extendable.business.color.boundary;

import java.io.Serializable;

import org.junit.Test;

public class CastTest {

    @Test
    public void testName() throws Exception {

        final Runnable r = this::printsomething;
        final Serializable sendSimpleNewsletterMethod = (Runnable & Serializable) this::printsomething;
        callTheExecution(sendSimpleNewsletterMethod);

    }

    public void callTheExecution(Serializable info) {
        final Runnable r = (Runnable) info;
        r.run();
    }

    private void printsomething() {
        System.out.println("Hello Duke...");
    }

}
