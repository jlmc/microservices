package org.xine.business.push.control;

import java.util.Random;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class Multiplier {

    private final Random random = new Random();

    @Asynchronous
    public Future<Long> multiply(final long a, final long b) {
        final long c = a * b;
        simulationOfLongComputation();
        return new AsyncResult<Long>(c);
    }

    private void simulationOfLongComputation() {
        try {
            Thread.sleep(this.random.nextInt(20) * 1000);
        } catch (final InterruptedException e) {
        }
    }

}
